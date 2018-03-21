package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;

/**
 * Generator for images with random colored lines.
 */
public class ColoredLinesGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final int count;

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
    }

    /**
     * The default constructor with unspecified lines count
     * (will be random, based on the size of image).
     */
    public ColoredLinesGenerator() {
        this(0);
    }

    /**
     * Constructor with specified lines count.
     *
     * @param count Should be bigger than 0. Otherwise it will be random (based on the size of image).
     */
    public ColoredLinesGenerator(int count) {
        this.count = count;
    }

    @Nullable
    @Override
    @WorkerThread
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);
        if (bitmap == null) {
            return null;
        }

        int biggestSide = Math.max(imageParams.getWidth(), imageParams.getHeight());
        int selectedCount = count;

        if (selectedCount <= 0) {
            float to = biggestSide / 10f * 20f;
            float from = to / 10f;
            selectedCount = (int) Math.ceil(Rig.random(from, to));
        }

        Canvas canvas = new Canvas(bitmap);
        Path line = new Path();
        RectF lineRect = new RectF();

        float startX = -imageParams.getWidth() / 2f;
        float startY = -imageParams.getHeight() / 2f;
        float zoneWidth = imageParams.getWidth() * 2f;
        float zoneHeight = imageParams.getHeight() * 2f;

        for (int i = 0; i < selectedCount; i++) {
            float cx = Rig.random(zoneWidth) + startX;
            float cy = Rig.random(zoneHeight) + startY;

            lineRect.set(Rig.random(zoneWidth) + startX,
                    Rig.random(zoneHeight) + startY,
                    Rig.random(zoneWidth) + startX,
                    Rig.random(zoneHeight) + startY);

            float startAngle = Rig.random(360f);
            float sweepAngle = Rig.random(360f);

            paint.setColor(imageParams.getPalette().getRandom());

            line.reset();
            line.moveTo(cx, cy);
            line.arcTo(lineRect, startAngle, sweepAngle, false);

            canvas.drawPath(line, paint);
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColoredLinesGenerator{" +
                "count=" + count +
                '}';
    }
}
