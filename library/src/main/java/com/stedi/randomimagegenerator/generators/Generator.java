package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.ImageParams;

public interface Generator {
    Bitmap generate(ImageParams imageParams) throws Exception;
}
