package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Generator for random colored pixelate image.
 * <p>Uses pixel multiplier, which represents one colored cell in pixels^2.</p>
 * <p>For example: if pixel multiplier is 5, then the colored cell will have 25 pixels.</p>
 */
public class ColoredPixelsGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final int pixelMultiplier;

    /**
     * The default constructor with pixel multiplier set to 1.
     */
    public ColoredPixelsGenerator() {
        this(1);
    }

    /**
     * Constructor with specified pixel multiplier.
     *
     * @param pixelMultiplier Must be bigger than 0.
     */
    public ColoredPixelsGenerator(int pixelMultiplier) {
        if (pixelMultiplier <= 0) {
            throw new IllegalArgumentException("pixelMultiplier must be bigger than 0");
        }
        this.pixelMultiplier = pixelMultiplier;
    }

    @Override
    @Nullable
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);
        if (bitmap == null) {
            return null;
        }

        Canvas canvas = new Canvas(bitmap);
        Rect pixel = new Rect();

        for (int x = 0; x < imageParams.getWidth(); x += pixelMultiplier) {
            for (int y = 0; y < imageParams.getHeight(); y += pixelMultiplier) {
                pixel.set(x, y, x + pixelMultiplier, y + pixelMultiplier);

                paint.setColor(imageParams.getPalette().getRandom());

                canvas.drawRect(pixel, paint);
            }
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColoredPixelsGenerator{" +
                "pixelMultiplier=" + pixelMultiplier +
                '}';
    }
}
