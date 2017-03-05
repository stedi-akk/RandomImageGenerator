package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

public class Quality {
    private final Bitmap.CompressFormat format;
    private final int value;

    public static Quality png() {
        return new Quality(Bitmap.CompressFormat.PNG, 100);
    }

    public static Quality jpg(int quality) {
        return new Quality(Bitmap.CompressFormat.JPEG, quality);
    }

    public Quality(Bitmap.CompressFormat format, int value) {
        this.format = format;
        this.value = value;
    }

    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    public int getQualityValue() {
        return value;
    }

    public String getFileExtension() {
        switch (getFormat()) {
            case JPEG:
                return "jpg";
            case PNG:
                return "png";
            case WEBP:
                return "webp";
        }
        return "png";
    }
}
