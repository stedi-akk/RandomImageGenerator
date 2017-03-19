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
    public void png() {
        iterateArgsAndTest(Quality.png());
    }

    @Test
    public void jpg() {
        iterateArgsAndTest(Quality.jpg(80));
    }

    @Test
    public void webp() {
        iterateArgsAndTest(new Quality(Bitmap.CompressFormat.WEBP, 100));
    }

    private void iterateArgsAndTest(Quality quality) {
        for (ColoredNoiseGenerator.Orientation orientation : ColoredNoiseGenerator.Orientation.values()) {
            for (ColoredNoiseGenerator.Type type : ColoredNoiseGenerator.Type.values()) {
                test(orientation, type, quality);
            }
        }
    }

    private void test(ColoredNoiseGenerator.Orientation orientation, ColoredNoiseGenerator.Type type, Quality quality) {
        ColoredNoiseGenerator generator = new ColoredNoiseGenerator(orientation, type);
        int count = 0;
        for (int width : sizes) {
            for (int height : sizes) {
                ImageParams params = new ImageParams(++count, width, height, null, quality);
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
