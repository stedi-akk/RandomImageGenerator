package com.stedi.randomimagegenerator;

public class ImageParams {
    private final int width, height;
    private final int number;
    private final String path;
    private final Format format;

    public ImageParams(int width, int height, int number, String path, Format format) {
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

    public String getPath() {
        return path;
    }

    public Format getFormat() {
        return format;
    }
}
