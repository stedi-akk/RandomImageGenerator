package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import static org.junit.Assert.*;
import org.junit.Test;

public class QualityTest {
    @Test(expected = IllegalArgumentException.class)
    public void exceptionNullCompressFormat() {
        new Quality(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWrongQualityValue1() {
        new Quality(Bitmap.CompressFormat.WEBP, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWrongQualityValue2() {
        new Quality(Bitmap.CompressFormat.WEBP, 200);
    }

    @Test
    public void staticPng() {
        Quality quality = Quality.png();

        assertNotNull(quality);
        assertEquals(quality.getQualityValue(), 100);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.PNG);
        assertEquals(quality.getFileExtension(), "png");
    }

    @Test
    public void staticJpg() {
        Quality quality = Quality.jpg(80);

        assertNotNull(quality);
        assertEquals(quality.getQualityValue(), 80);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.JPEG);
        assertEquals(quality.getFileExtension(), "jpg");
    }

    @Test
    public void generalTest() {
        Quality quality = new Quality(Bitmap.CompressFormat.WEBP, 100);

        assertEquals(quality.getQualityValue(), 100);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.WEBP);
        assertEquals(quality.getFileExtension(), "webp");

        quality.setQualityValue(66);

        assertEquals(quality.getQualityValue(), 66);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.WEBP);
        assertEquals(quality.getFileExtension(), "webp");

        quality.setFormat(Bitmap.CompressFormat.PNG);

        assertEquals(quality.getQualityValue(), 100);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.PNG);
        assertEquals(quality.getFileExtension(), "png");

        quality.setQualityValue(10);

        assertEquals(quality.getQualityValue(), 100);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.PNG);
        assertEquals(quality.getFileExtension(), "png");

        quality.setFormat(Bitmap.CompressFormat.JPEG);

        assertEquals(quality.getQualityValue(), 100);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.JPEG);
        assertEquals(quality.getFileExtension(), "jpg");

        quality.setQualityValue(0);

        assertEquals(quality.getQualityValue(), 0);
        assertSame(quality.getFormat(), Bitmap.CompressFormat.JPEG);
        assertEquals(quality.getFileExtension(), "jpg");
    }
}
