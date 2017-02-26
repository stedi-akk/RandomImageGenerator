package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import java.io.File;

public interface SaveCallback {
    void onSaved(Bitmap bitmap, File path);

    void onException(Bitmap bitmap, File path, Exception e);
}
