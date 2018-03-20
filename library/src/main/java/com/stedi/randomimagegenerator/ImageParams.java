package com.stedi.randomimagegenerator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Image parameters used to generate one unique image. Created internally by {@link Rig}.
 */
public class ImageParams {
    private final int id;
    private final int width, height;
    private final File path;
    private final Quality quality;
    private final RigPalette palette;
    private final FileNamePolicy fileNamePolicy;

    ImageParams(int id, int width, int height, @Nullable File path, @NonNull Quality quality, @NonNull RigPalette palette, @Nullable FileNamePolicy fileNamePolicy) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.path = path;
        this.quality = quality;
        this.palette = palette;
        this.fileNamePolicy = fileNamePolicy;
    }

    /**
     * @return Unique image id (number of image).
     */
    public int getId() {
        return id;
    }

    /**
     * @return Width of requested image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Height of requested image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The path where image should be saved.
     */
    @Nullable
    public File getPath() {
        return path;
    }

    /**
     * @return The quality of requested image.
     */
    @NonNull
    public Quality getQuality() {
        return quality;
    }

    /**
     * @return The palette of requested image.
     */
    @NonNull
    public RigPalette getPalette() {
        return palette;
    }

    /**
     * @return The file name policy of requested image.
     */
    @Nullable
    public FileNamePolicy getFileNamePolicy() {
        return fileNamePolicy;
    }

    @Override
    public String toString() {
        return "ImageParams{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", path=" + path +
                ", quality=" + quality +
                ", palette=" + palette +
                ", fileNamePolicy=" + fileNamePolicy +
                '}';
    }
}
