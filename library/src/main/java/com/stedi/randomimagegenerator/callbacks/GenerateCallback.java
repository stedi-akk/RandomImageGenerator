package com.stedi.randomimagegenerator.callbacks;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.ImageParams;

public interface GenerateCallback {
    void onGenerated(ImageParams imageParams, Bitmap bitmap);

    void onFailedToGenerate(ImageParams imageParams, Exception e);
}
