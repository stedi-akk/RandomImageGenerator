package com.stedi.randomimagegenerator.callbacks;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.ImageParams;

/**
 * Interface definition for a callback to be invoked when an image is generated or not.
 */
public interface GenerateCallback {
    /**
     * Called when an image is generated.
     *
     * @param imageParams The parameters that was used to generate image.
     * @param bitmap      The image that was generated.
     */
    void onGenerated(ImageParams imageParams, Bitmap bitmap);

    /**
     * Called when an image is not generated.
     *
     * @param imageParams The parameters that was used to generate image.
     * @param e           The detailed exception.
     */
    void onFailedToGenerate(ImageParams imageParams, Exception e);
}
