package com.stedi.randomimagegenerator.generators.effects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.generators.Generator;

/**
 * Generator wrapper, that creates threshold effect from the target generator.
 */
public class ThresholdEffect implements Generator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Generator generator;
    private final int colorA;
    private final int colorB;

    {
        ColorMatrix grayscaleMatrix = new ColorMatrix();
        grayscaleMatrix.setSaturation(0f);
        paint.setColorFilter(new ColorMatrixColorFilter(grayscaleMatrix));
    }

    /**
     * The default constructor with black and white threshold colors.
     *
     * @param generator The target generator.
     */
    public ThresholdEffect(@NonNull Generator generator) {
        this(generator, Color.BLACK, Color.WHITE);
    }

    /**
     * Constructor with specified threshold colors.
     *
     * @param generator The target generator.
     * @param colorA    The first threshold color.
     * @param colorB    The second threshold color.
     */
    public ThresholdEffect(@NonNull Generator generator, int colorA, int colorB) {
        if (generator == null) {
            throw new IllegalArgumentException("target generator must not be null");
        }
        this.generator = generator;
        this.colorA = colorA;
        this.colorB = colorB;
    }

    @Nullable
    @Override
    @WorkerThread
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap targetBitmap = generator.generate(imageParams);
        if (targetBitmap == null) {
            return null;
        }

        Bitmap resultBitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(targetBitmap, 0, 0, paint);
        targetBitmap.recycle();

        int[] pixels = new int[imageParams.getWidth() * imageParams.getHeight()];
        resultBitmap.getPixels(pixels, 0, imageParams.getWidth(), 0, 0, imageParams.getWidth(), imageParams.getHeight());

        for (int i = 0; i < pixels.length; i++) {
            int threshold = Color.red(pixels[i]);
            if (threshold < 127) {
                pixels[i] = colorA;
            } else {
                pixels[i] = colorB;
            }
        }

        resultBitmap.setPixels(pixels, 0, imageParams.getWidth(), 0, 0, imageParams.getWidth(), imageParams.getHeight());

        return resultBitmap;
    }

    @Override
    public String toString() {
        return "ThresholdEffect{" +
                "generator=" + generator +
                ", colorA=" + colorA +
                ", colorB=" + colorB +
                '}';
    }
}
