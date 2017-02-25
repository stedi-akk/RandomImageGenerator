package com.stedi.randomimagegenerator;

import java.io.File;

public class ImageParams {
    private final int width, height;
    private final int number;
    private final File path;
    private final Quality quality;

    public ImageParams(int width, int height, int number, File path, Quality quality) {
        this.width = width;
        this.height = height;
        this.number = number;
        this.path = path;
        this.quality = quality;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumber() {
        return number;
    }

    public File getPath() {
        return path;
    }

    public Quality getQuality() {
        return quality;
    }
}
