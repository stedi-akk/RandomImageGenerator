package com.stedi.randomimagegenerator;

import android.support.annotation.NonNull;

/**
 * Image file name policy. Used to save images with the corresponding names retrieved from it.
 */
public interface FileNamePolicy {
    /**
     * Called before an image will be saved to a specified path.
     *
     * @param imageParams Image parameters used to generate an image.
     * @return Image file name.
     */
    @NonNull
    String getName(@NonNull ImageParams imageParams);
}
