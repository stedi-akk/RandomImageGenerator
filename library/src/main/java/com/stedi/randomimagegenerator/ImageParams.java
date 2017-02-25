package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import java.io.File;

public class ImageParams {
    private final int width, height;
    private final int number;
    private final File path;
    private final Bitmap.CompressFormat format;

    public ImageParams(int width, int height, int number, File path, Bitmap.CompressFormat format) {
        this.width = width;
        this.height = height;
        this.number = number;
        this.path = path;
        this.format = format;
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

    public Bitmap.CompressFormat getFormat() {
        return format;
    }
}
