package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

public interface GenerateCallback {
    void onGenerated(ImageParams imageParams, Bitmap bitmap);

    void onException(ImageParams imageParams, Exception e);
}
