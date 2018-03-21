package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.ColoredCirclesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredLinesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredNoiseGenerator;
import com.stedi.randomimagegenerator.generators.ColoredPixelsGenerator;
import com.stedi.randomimagegenerator.generators.ColoredRectangleGenerator;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.Generator;
import com.stedi.randomimagegenerator.generators.effects.ThresholdEffect;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ThresholdEffectTest {
    private final int[] sizes = new int[]{1, 2, 63, 256};

    @Test(expected = IllegalArgumentException.class)
    public void testNotNull() {
        new ThresholdEffect(null);
    }

    @Test
    public void png() {
        testWithDefaultGenerators(Quality.png());
    }

    @Test
    public void jpg() {
        testWithDefaultGenerators(Quality.jpg(80));
    }

    @Test
    public void webp() {
        testWithDefaultGenerators(new Quality(Bitmap.CompressFormat.WEBP, 100));
    }

    private void testWithDefaultGenerators(Quality quality) {
        test(new FlatColorGenerator(), quality);
        test(new ColoredRectangleGenerator(), quality);
        test(new ColoredPixelsGenerator(), quality);
        test(new ColoredNoiseGenerator(), quality);
        test(new ColoredCirclesGenerator(), quality);
        test(new ColoredLinesGenerator(), quality);
    }

    private void test(Generator target, Quality quality) {
        ThresholdEffect effect = new ThresholdEffect(target);
        int count = 0;
        for (int width : sizes) {
            for (int height : sizes) {
                ImageParams params = new ImageParams(++count, width, height, null, quality, RigPalette.allColors(), null);
                try {
                    Bitmap bitmap = effect.generate(params);
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
