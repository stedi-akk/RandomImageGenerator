package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class FlatColorGeneratorTest {
    private final int[] sizes = new int[]{1, 2, 63, 512};

    @Test
    public void png() {
        test(Quality.png());
    }

    @Test
    public void jpg() {
        test(Quality.jpg(80));
    }

    @Test
    public void webp() {
        test(new Quality(Bitmap.CompressFormat.WEBP, 100));
    }

    private void test(Quality quality) {
        FlatColorGenerator generator = new FlatColorGenerator();
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
