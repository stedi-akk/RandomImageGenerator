package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.stedi.randomimagegenerator.ImageParams;

public class MirroredGenerator implements Generator {
    private final Paint paint = new Paint();

    private final Generator generator;

    public MirroredGenerator(Generator generator) {
        this.generator = generator;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap targetBitmap = generator.generate(imageParams);
        if (targetBitmap == null)
            return null;

        Bitmap partBitmap = Bitmap.createScaledBitmap(targetBitmap,
                targetBitmap.getWidth() / 2, targetBitmap.getHeight() / 2, true);
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
}
