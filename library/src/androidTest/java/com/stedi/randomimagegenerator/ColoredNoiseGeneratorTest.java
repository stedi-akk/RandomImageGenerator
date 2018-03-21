package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.ColoredNoiseGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ColoredNoiseGeneratorTest {
    private final int[] sizes = new int[]{1, 33, 128};

    @Test
    public void incorrectArgs() {
        try {
            new ColoredNoiseGenerator(null, null, -1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new ColoredNoiseGenerator(ColoredNoiseGenerator.Orientation.HORIZONTAL, null, -1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new ColoredNoiseGenerator(ColoredNoiseGenerator.Orientation.HORIZONTAL, ColoredNoiseGenerator.Type.TYPE_1, -1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void png() {
        iterateGeneratorArgsAndTest(1, Quality.png());
        iterateGeneratorArgsAndTest(10, Quality.png());
        iterateGeneratorArgsAndTest(200, Quality.png());
    }

    @Test
    public void jpg() {
        iterateGeneratorArgsAndTest(1, Quality.jpg(80));
        iterateGeneratorArgsAndTest(10, Quality.jpg(80));
        iterateGeneratorArgsAndTest(200, Quality.jpg(80));
    }

    @Test
    public void webp() {
        iterateGeneratorArgsAndTest(1, new Quality(Bitmap.CompressFormat.WEBP, 100));
        iterateGeneratorArgsAndTest(10, new Quality(Bitmap.CompressFormat.WEBP, 100));
        iterateGeneratorArgsAndTest(200, new Quality(Bitmap.CompressFormat.WEBP, 100));
    }

    private void iterateGeneratorArgsAndTest(int pixelMultiplier, Quality quality) {
        for (ColoredNoiseGenerator.Orientation orientation : ColoredNoiseGenerator.Orientation.values()) {
            for (ColoredNoiseGenerator.Type type : ColoredNoiseGenerator.Type.values()) {
                test(orientation, type, quality, pixelMultiplier);
            }
        }
    }

    private void test(ColoredNoiseGenerator.Orientation orientation, ColoredNoiseGenerator.Type type, Quality quality, int pixelMultiplier) {
        ColoredNoiseGenerator generator = new ColoredNoiseGenerator(orientation, type, pixelMultiplier);
        int count = 0;
        for (int width : sizes) {
            for (int height : sizes) {
                ImageParams params = new ImageParams(++count, width, height, null, quality, RigPalette.allColors(), null);
                try {
                    Bitmap bitmap = generator.generate(params);
                    assertNotNull(bitmap);
                    assertEquals(bitmap.getWidth(), width);
                    assertEquals(bitmap.getHeight(), height);
                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail();
                }
            }
        }
    }
}
