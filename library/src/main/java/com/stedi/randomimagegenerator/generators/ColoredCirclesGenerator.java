package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.ImageParams;

import java.util.Objects;

/**
 * Generator for image with random colored circles.
 */
public class ColoredCirclesGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int count;

    /**
     * The default constructor with unspecified circles count
     * (will be random, based on the size of image).
     */
    public ColoredCirclesGenerator() {
        this(0);
    }

    /**
     * Constructor with specified circles count.
     *
     * @param count Should be bigger than 0. Otherwise, count will be random, based on the size of image.
     */
    public ColoredCirclesGenerator(int count) {
        this.count = count;
    }

    @Override
    @Nullable
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);
        if (bitmap == null) {
            return null;
        }

        int biggestSide = Math.max(imageParams.getWidth(), imageParams.getHeight());

        if (count <= 0) {
            count = (int) Math.ceil(Math.random() * ((biggestSide / 100f) * 20f));
        }

        Canvas canvas = new Canvas(bitmap);

        float radiusFrom = biggestSide / 12f;
        float radiusTo = biggestSide / 4f;

        for (int i = 0; i < count; i++) {
            float cx = (float) (Math.random() * imageParams.getWidth());
            float cy = (float) (Math.random() * imageParams.getHeight());
            float radius = (float) ((Math.random() * (radiusTo - radiusFrom)) + radiusFrom);

            paint.setColor(getRandomColor());

            canvas.drawCircle(cx, cy, radius, paint);
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColoredCirclesGenerator{" +
                "count=" + count +
                '}';
    }
}
