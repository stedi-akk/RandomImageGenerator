package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import java.io.File;

public interface SaveCallback {
    void onSaved(Bitmap bitmap, File file);

    void onException(Bitmap bitmap, File file, Exception e);
}
