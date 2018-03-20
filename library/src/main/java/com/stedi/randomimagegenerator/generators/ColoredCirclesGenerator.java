package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;

/**
 * Generator for images with random colored circles.
 */
public class ColoredCirclesGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final int count;

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
     * @param count Should be bigger than 0. Otherwise it will be random (based on the size of image).
     */
    public ColoredCirclesGenerator(int count) {
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

        float radiusFrom = biggestSide / 12f;
        float radiusTo = biggestSide / 4f;

        for (int i = 0; i < selectedCount; i++) {
            float cx = Rig.random(imageParams.getWidth());
            float cy = Rig.random(imageParams.getHeight());
            float radius = Rig.random(radiusFrom, radiusTo);

            paint.setColor(imageParams.getPalette().getRandom());

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
