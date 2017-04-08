package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Generator for random colored pixelate image.
 * <p>Uses pixel multiplier, which represents one colored cell in pixels^2.</p>
 * <p>For example: if pixel multiplier is 5, then the colored cell will have 25 pixels.</p>
 */
public class ColoredPixelsGenerator extends FlatColorGenerator {
    private final Paint paint = new Paint();

    private final int pixelMultiplier;

    /**
     * The default constructor with pixel multiplier set to 1.
     */
    public ColoredPixelsGenerator() {
        this(1);
    }

    /**
     * Constructor with specified pixel multiplier.
     *
     * @param pixelMultiplier Must be > 0.
     */
    public ColoredPixelsGenerator(int pixelMultiplier) {
        if (pixelMultiplier <= 0)
            throw new IllegalArgumentException("pixelMultiplier must be > 0");
        this.pixelMultiplier = pixelMultiplier;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = super.generate(imageParams);

        Canvas canvas = new Canvas(bitmap);
        Rect pixel = new Rect();

        for (int x = 0; x < imageParams.getWidth(); x += pixelMultiplier) {
            for (int y = 0; y < imageParams.getHeight(); y += pixelMultiplier) {
                pixel.set(x, y, x + pixelMultiplier, y + pixelMultiplier);

                paint.setColor(getRandomColor());

                canvas.drawRect(pixel, paint);
            }
        }

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColoredPixelsGenerator{" +
                "pixelMultiplier=" + pixelMultiplier +
                '}';
    }
}
