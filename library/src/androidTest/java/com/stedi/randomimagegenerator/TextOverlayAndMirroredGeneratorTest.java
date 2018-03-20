package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.ColoredCirclesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredNoiseGenerator;
import com.stedi.randomimagegenerator.generators.ColoredPixelsGenerator;
import com.stedi.randomimagegenerator.generators.ColoredRectangleGenerator;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.Generator;
import com.stedi.randomimagegenerator.generators.MirroredGenerator;
import com.stedi.randomimagegenerator.generators.TextOverlayGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TextOverlayAndMirroredGeneratorTest {
    private final int[] sizes = new int[]{1, 2, 63, 256};

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
        wrapAndTest(new FlatColorGenerator(), quality);
        wrapAndTest(new ColoredRectangleGenerator(), quality);
        wrapAndTest(new ColoredPixelsGenerator(), quality);
        wrapAndTest(new ColoredNoiseGenerator(), quality);
        wrapAndTest(new ColoredCirclesGenerator(), quality);
    }

    private void wrapAndTest(Generator target, Quality quality) {
        test(new TextOverlayGenerator.Builder()
                .setGenerator(new MirroredGenerator(target))
                .build(), quality);
    }

    private void test(Generator generator, Quality quality) {
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
