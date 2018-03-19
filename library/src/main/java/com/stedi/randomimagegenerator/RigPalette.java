package com.stedi.randomimagegenerator;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * Is used by generators to generate images with specified palette.
 */
public class RigPalette {
    private final float[] hsv = new float[3];

    private float hueFrom;
    private float hueTo;

    private boolean useLightColors;
    private boolean useDarkColors;

    private boolean isBlackAndWhite;

    /**
     * @return Palette with all available HSV colors.
     */
    @NonNull
    public static RigPalette allColors() {
        return new RigPalette(0f, 360f, true, true);
    }

    /**
     * @return Palette from one hue color, but with saturation and lightness. Hue color is picked from the passed ARGB color.
     */
    @NonNull
    public static RigPalette fromColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return new RigPalette(hsv[0], hsv[0], true, true);
    }

    /**
     * @param hueFrom 0..360.
     * @param hueTo   0..360.
     * @return Palette from one hue color to another hue color. Without saturation and lightness.
     */
    @NonNull
    public static RigPalette hueRange(float hueFrom, float hueTo) {
        return new RigPalette(hueFrom, hueTo, false, false);
    }

    /**
     * @return Black and white palette (grayscale).
     */
    @NonNull
    public static RigPalette blackAndWhite() {
        RigPalette palette = new RigPalette(0f, 0f, false, false);
        palette.setBlackAndWhite(true);
        return palette;
    }

    public RigPalette(float hueFrom, float hueTo, boolean useLightColors, boolean useDarkColors) {
        setHueFrom(hueFrom);
        setHueTo(hueTo);
        setUseLightColors(useLightColors);
        setUseDarkColors(useDarkColors);
    }

    public float getHueFrom() {
        return hueFrom;
    }

    /**
     * @param hueFrom 0..360
     */
    public void setHueFrom(float hueFrom) {
        if (hueFrom < 0 || hueFrom > 360) {
            throw new IllegalArgumentException("hue must be 0..360");
        }
        this.hueFrom = hueFrom;
    }

    public float getHueTo() {
        return hueTo;
    }

    /**
     * @param hueTo 0..360
     */
    public void setHueTo(float hueTo) {
        if (hueTo < 0 || hueTo > 360) {
            throw new IllegalArgumentException("hue must be 0..360");
        }
        this.hueTo = hueTo;
    }

    /**
     * Is HSV saturation enabled, or not.
     */
    public boolean isUseLightColors() {
        return useLightColors;
    }

    /**
     * To enable HSV saturation in the palette.
     */
    public void setUseLightColors(boolean useLightColors) {
        this.useLightColors = useLightColors;
    }

    /**
     * Is HSV lightness enabled, or not.
     */
    public boolean isUseDarkColors() {
        return useDarkColors;
    }

    /**
     * To enable HSV lightness in the palette.
     */
    public void setUseDarkColors(boolean useDarkColors) {
        this.useDarkColors = useDarkColors;
    }

    /**
     * Is black and white palette only, or not.
     */
    public boolean isBlackAndWhite() {
        return isBlackAndWhite;
    }

    /**
     * To enable black and white palette (grayscale).
     */
    public void setBlackAndWhite(boolean isBlackAndWhite) {
        this.isBlackAndWhite = isBlackAndWhite;
    }

    /**
     * Will return random RGB color from the palette.
     */
    @ColorInt
    public int getRandom() {
        if (isBlackAndWhite) {
            hsv[0] = 0f;
            hsv[1] = 0f;
            hsv[2] = random(0f, 1f);
        } else {
            hsv[0] = random(hueFrom, hueTo);
            hsv[1] = useLightColors ? random(0f, 1f) : 1f;
            hsv[2] = useDarkColors ? random(0f, 1f) : 1f;
        }
        return Color.HSVToColor(hsv);
    }

    private float random(float from, float to) {
        if (from == to) {
            return from;
        }

        if (to < from) {
            float temp = from;
            from = to;
            to = temp;
        }

        return (float) (Math.random() * ((to - from) + 1f) + from);
    }

    @Override
    public String toString() {
        return "RigPalette{" +
                "hueFrom=" + hueFrom +
                ", hueTo=" + hueTo +
                ", useLightColors=" + useLightColors +
                ", useDarkColors=" + useDarkColors +
                ", isBlackAndWhite=" + isBlackAndWhite +
                '}';
    }
}
