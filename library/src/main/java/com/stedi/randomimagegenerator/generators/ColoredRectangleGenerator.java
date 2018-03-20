package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;

/**
 * Generator for images with random colored rectangles.
 */
public class ColoredRectangleGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final int count;

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
     * @param count Should be bigger than 0. Otherwise it will be random (based on the size of image).
     */
    public ColoredRectangleGenerator(int count) {
        this.count = count;
    }

    @Override
    @Nullable
    @WorkerThread
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);
        if (bitmap == null) {
            return null;
        }

        int biggestSide = Math.max(imageParams.getWidth(), imageParams.getHeight());
        int selectedCount = count;

        if (selectedCount <= 0) {
            float to = biggestSide / 100f * 20f;
            float from = to / 10f;
            selectedCount = (int) Math.ceil(Rig.random(from, to));
        }

        Canvas canvas = new Canvas(bitmap);

        float widthFrom = biggestSide / 6f;
        float widthTo = biggestSide / 2f;
        float heightFrom = widthFrom;
        float heightTo = widthTo;

        RectF rect = new RectF();

        for (int i = 0; i < selectedCount; i++) {
            float left = Rig.random(imageParams.getWidth()) - widthTo / 2f;
            float top = Rig.random(imageParams.getHeight()) - heightTo / 2f;
            float right = left + Rig.random(widthFrom, widthTo);
            float bottom = top + Rig.random(heightFrom, heightTo);
            rect.set(left, top, right, bottom);

            paint.setColor(imageParams.getPalette().getRandom());

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
