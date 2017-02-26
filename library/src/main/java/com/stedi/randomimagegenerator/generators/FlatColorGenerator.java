package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.stedi.randomimagegenerator.ImageParams;

public class FlatColorGenerator implements Generator {
    @Override
    public Bitmap generate(ImageParams imageParams) throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(imageParams.getWidth(), imageParams.getHeight(), Bitmap.Config.ARGB_8888);
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        bitmap.eraseColor(Color.rgb(r, g, b));
        return bitmap;
    }
}
