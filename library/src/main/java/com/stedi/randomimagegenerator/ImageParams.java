package com.stedi.randomimagegenerator;

import java.io.File;

public class ImageParams {
    private final int id;
    private final int width, height;
    private final File path;
    private final Quality quality;

    public ImageParams(int id, int width, int height, File path, Quality quality) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.path = path;
        this.quality = quality;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public File getPath() {
        return path;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "ImageParams{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", path=" + path +
                ", quality=" + quality +
                '}';
    }
}
