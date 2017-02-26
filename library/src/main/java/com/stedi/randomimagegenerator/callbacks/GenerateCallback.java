package com.stedi.randomimagegenerator.callbacks;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.ImageParams;

public interface GenerateCallback {
    void onGenerated(ImageParams imageParams, Bitmap bitmap);

    void onException(ImageParams imageParams, Exception e);
}
