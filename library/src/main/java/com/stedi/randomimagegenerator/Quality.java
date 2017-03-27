package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

/**
 * A specified quality to use for bitmap generation and compression.
 */
public class Quality {
    private final Bitmap.CompressFormat format;
    private final int value;

    /**
     * Static creation of the default PNG quality (value 100).
     *
     * @return The default PNG quality.
     */
    public static Quality png() {
        return new Quality(Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * Static creation of specified JPEG quality.
     *
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting.
     * @return JPEG quality instance.
     */
    public static Quality jpg(int quality) {
        return new Quality(Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * Constructs a new quality object with specified format and quality value.
     *
     * @param format Specifies the known formats a bitmap can be compressed into.
     * @param value  Hint to the compressor, 0-100. 0 meaning compress for
     *               small size, 100 meaning compress for max quality. Some
     *               formats, like PNG which is lossless, will ignore the
     *               quality setting.
     */
    public Quality(Bitmap.CompressFormat format, int value) {
        if (format == null)
            throw new IllegalArgumentException("format cannot not be null");
        if (value < 0 || value > 100)
            throw new IllegalArgumentException("quality value must be 0..100");
        this.format = format;
        this.value = value;
    }

    /**
     * @return Quality compress format.
     */
    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    /**
     * @return Quality compress value (0-100).
     */
    public int getQualityValue() {
        return value;
    }

    /**
     * @return File extension based on the quality format.
     */
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

    @Override
    public String toString() {
        return "Quality{" +
                "format=" + format +
                ", value=" + value +
                '}';
    }
}
