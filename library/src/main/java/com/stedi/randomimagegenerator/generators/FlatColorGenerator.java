package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Simple random flat color image generator.
 */
public class FlatColorGenerator implements Generator {
    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(getRandomColor());
        return bitmap;
    }

    protected int getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return Color.rgb(r, g, b);
    }

    @Override
    public String toString() {
        return "FlatColorGenerator{}";
    }
}
