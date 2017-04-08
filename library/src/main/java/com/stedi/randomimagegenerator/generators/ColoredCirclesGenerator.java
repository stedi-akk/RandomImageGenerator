package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Generator for image with random colored circles.
 */
public class ColoredCirclesGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint();

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
     * @param count Must be bigger than 0.
     */
    public ColoredCirclesGenerator(int count) {
        this.count = count;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);

        int biggestSide = Math.max(imageParams.getWidth(), imageParams.getHeight());

        if (count <= 0)
            count = (int) Math.ceil(Math.random() * ((biggestSide / 100f) * 20f));

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
