package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stedi.randomimagegenerator.ImageParams;

public class ColoredPixelsGenerator implements Generator {
    private final Paint paint = new Paint();

    private final int pixelMultiplier;

    public ColoredPixelsGenerator() {
        this(1);
    }

    public ColoredPixelsGenerator(int pixelMultiplier) {
        if (pixelMultiplier <= 0)
            throw new IllegalArgumentException("pixelMultiplier must be > 0");
        this.pixelMultiplier = pixelMultiplier;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.BLACK);

        Canvas canvas = new Canvas(bitmap);
        Rect pixel = new Rect();

        for (int x = 0; x < imageParams.getWidth(); x += pixelMultiplier) {
            for (int y = 0; y < imageParams.getHeight(); y += pixelMultiplier) {
                pixel.set(x, y, x + pixelMultiplier, y + pixelMultiplier);

                int r = (int) (Math.random() * 256);
                int g = (int) (Math.random() * 256);
                int b = (int) (Math.random() * 256);

                paint.setColor(Color.rgb(r, g, b));
                canvas.drawRect(pixel, paint);
            }
        }

        return bitmap;
    }
}
