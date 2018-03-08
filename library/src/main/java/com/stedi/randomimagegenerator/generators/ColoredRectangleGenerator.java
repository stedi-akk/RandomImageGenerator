package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Generator for image with random colored rectangles.
 */
public class ColoredRectangleGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int count;

    /**
     * The default constructor with unspecified rectangles count
     * (will be random, based on the size of image).
     */
    public ColoredRectangleGenerator() {
        this(0);
    }

    /**
     * Constructor with specified rectangles count.
     *
     * @param count Should be bigger than 0. Otherwise, count will be random, based on the size of image.
     */
    public ColoredRectangleGenerator(int count) {
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

        float widthFrom = biggestSide / 4f;
        float widthTo = biggestSide;
        float heightFrom = biggestSide / 4f;
        float heightTo = biggestSide;

        for (int i = 0; i < count; i++) {
            Rect rect = new Rect();

            int left = (int) ((Math.random() * imageParams.getWidth()) - imageParams.getWidth() / 2f);
            int top = (int) ((Math.random() * imageParams.getHeight()) - imageParams.getHeight() / 2f);
            int right = (int) (left + ((Math.random() * (widthTo - widthFrom)) + widthFrom));
            int bottom = (int) (top + ((Math.random() * (heightTo - heightFrom)) + heightFrom));
            rect.set(left, top, right, bottom);

            paint.setColor(getRandomColor());

            canvas.drawRect(rect, paint);
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColoredRectangleGenerator{" +
                "count=" + count +
                '}';
    }
}
