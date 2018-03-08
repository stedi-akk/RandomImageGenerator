package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Generator wrapper, that creates mirrored effect for the target generator.
 */
public class MirroredGenerator implements Generator {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Generator generator;

    /**
     * @param generator The target generator.
     */
    public MirroredGenerator(@NonNull Generator generator) {
        if (generator == null) {
            throw new IllegalArgumentException("target generator must not be null");
        }
        this.generator = generator;
    }

    @Override
    @Nullable
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        Bitmap targetBitmap = generator.generate(imageParams);
        if (targetBitmap == null) {
            return null;
        }

        int scaledWidth = (int) Math.ceil(targetBitmap.getWidth() / 2f);
        int scaledHeight = (int) Math.ceil(targetBitmap.getHeight() / 2f);
        Bitmap partBitmap = Bitmap.createScaledBitmap(targetBitmap, scaledWidth, scaledHeight, true);
        targetBitmap.recycle();

        Bitmap resultBitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);

        for (float x = 0, scaleX = 1, xPart = 1; x < resultBitmap.getWidth(); x += resultBitmap.getWidth() / 2f, scaleX -= 2, xPart++) {
            for (float y = 0, scaleY = 1, yPart = 1; y < resultBitmap.getHeight(); y += resultBitmap.getHeight() / 2f, scaleY -= 2, yPart++) {
                canvas.save();
                canvas.scale(scaleX, scaleY);
                canvas.drawBitmap(partBitmap, x * xPart * scaleX, y * yPart * scaleY, paint);
                canvas.restore();
            }
        }
        partBitmap.recycle();

        return resultBitmap;
    }

    @Override
    public String toString() {
        return "MirroredGenerator{" +
                "generator=" + generator +
                '}';
    }
}
