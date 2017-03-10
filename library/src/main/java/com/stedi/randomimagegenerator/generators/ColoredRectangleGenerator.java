package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stedi.randomimagegenerator.ImageParams;

public class ColoredRectangleGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint();

    private int count;

    public ColoredRectangleGenerator() {
        this(0);
    }

    public ColoredRectangleGenerator(int count) {
        this.count = count;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);

        int biggestSide = Math.max(imageParams.getWidth(), imageParams.getHeight());

        if (count <= 0)
            count = (int) Math.ceil(Math.random() * ((biggestSide / 100f) * 20f));

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
