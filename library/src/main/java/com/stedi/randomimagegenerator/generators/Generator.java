package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;

/**
 * Interface definition for a generator, that is used to generate an image ({@link Bitmap}).
 */
public interface Generator {
    /**
     * Called from {@link Rig} when a new image should be generated.
     *
     * @param imageParams Image parameters used to generate an image.
     * @return Bitmap object, or null.
     * @throws Exception If any.
     */
    @Nullable
    Bitmap generate(@NonNull ImageParams imageParams) throws Exception;
}
