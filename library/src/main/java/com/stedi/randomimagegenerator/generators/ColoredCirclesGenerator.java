package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.stedi.randomimagegenerator.ImageParams;

public class ColoredCirclesGenerator implements Generator {
    private final Paint paint = new Paint();

    private int count;

    public ColoredCirclesGenerator() {
        this(0);
    }

    public ColoredCirclesGenerator(int count) {
        this.count = count;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(getRandomColor());

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

    private int getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return Color.rgb(r, g, b);
    }
}
