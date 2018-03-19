package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Generator for image with random colored noise.
 */
public class ColoredNoiseGenerator implements Generator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Orientation selectedOrientation;
    private final Type selectedType;
    private final int pixelMultiplier;

    public enum Orientation {
        VERTICAL,
        HORIZONTAL,
        RANDOM
    }

    public enum Type {
        TYPE_1,
        TYPE_2,
        TYPE_3,
        TYPE_4,
        TYPE_5,
        TYPE_6,
        RANDOM
    }

    {
        ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0f);
        paint.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));
    }

    /**
     * The default constructor with random orientation and type, with pixel multiplier set to 2.
     */
    public ColoredNoiseGenerator() {
        this(Orientation.RANDOM, Type.RANDOM);
    }

    /**
     * Constructor with specified orientation and type, with pixel multiplier set to 2.
     */
    public ColoredNoiseGenerator(@NonNull Orientation orientation, @NonNull Type type) {
        this(orientation, type, 2);
    }

    /**
     * Constructor with specified orientation, type and pixel multiplier.
     *
     * @param pixelMultiplier Must be bigger than 0.
     */
    public ColoredNoiseGenerator(@NonNull Orientation orientation, @NonNull Type type, int pixelMultiplier) {
        if (orientation == null || type == null) {
            throw new IllegalArgumentException("arguments must not be null");
        }
        if (pixelMultiplier <= 0) {
            throw new IllegalArgumentException("pixelMultiplier must be bigger than 0");
        }
        this.selectedOrientation = orientation;
        this.selectedType = type;
        this.pixelMultiplier = pixelMultiplier;
    }

    @Override
    @Nullable
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Orientation orientation;
        if (selectedOrientation == Orientation.RANDOM) {
            orientation = Math.random() < 0.5d ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        } else {
            orientation = selectedOrientation;
        }

        Type type;
        if (selectedType == Type.RANDOM) {
            type = Type.values()[(int) (Math.random() * 6)];
        } else {
            type = selectedType;
        }

        int iMax = orientation == Orientation.HORIZONTAL ? imageParams.getHeight() : imageParams.getWidth();
        int jMax = orientation == Orientation.HORIZONTAL ? imageParams.getWidth() : imageParams.getHeight();

        Rect pixel = new Rect(0, 0, pixelMultiplier, pixelMultiplier);

        int rgb1 = (int) (Math.random() * 256);
        for (int i = 0; i < iMax; i += pixelMultiplier) {
            int rgb2 = (int) (Math.random() * 256);
            for (int j = 0; j < jMax; j += pixelMultiplier) {
                int rgb3 = (int) (Math.random() * 256);

                if (type == Type.TYPE_1) {
                    paint.setColor(Color.rgb(rgb1, rgb2, rgb3));
                } else if (type == Type.TYPE_2) {
                    paint.setColor(Color.rgb(rgb1, rgb3, rgb2));
                } else if (type == Type.TYPE_3) {
                    paint.setColor(Color.rgb(rgb2, rgb1, rgb3));
                } else if (type == Type.TYPE_4) {
                    paint.setColor(Color.rgb(rgb2, rgb3, rgb1));
                } else if (type == Type.TYPE_5) {
                    paint.setColor(Color.rgb(rgb3, rgb1, rgb2));
                } else if (type == Type.TYPE_6) {
                    paint.setColor(Color.rgb(rgb3, rgb2, rgb1));
                }

                if (orientation == Orientation.VERTICAL) {
                    pixel.offsetTo(i, j);
                } else if (orientation == Orientation.HORIZONTAL) {
                    pixel.offsetTo(j, i);
                }

                canvas.drawRect(pixel, paint);
            }
        }

        if (!imageParams.getPalette().isBlackAndWhite()) {
            Paint palettePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            palettePaint.setColorFilter(new PorterDuffColorFilter(imageParams.getPalette().getRandom(), PorterDuff.Mode.OVERLAY));
            canvas.drawBitmap(bitmap, 0, 0, palettePaint);
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColoredNoiseGenerator{" +
                "selectedOrientation=" + selectedOrientation +
                ", selectedType=" + selectedType +
                ", pixelMultiplier=" + pixelMultiplier +
                '}';
    }
}