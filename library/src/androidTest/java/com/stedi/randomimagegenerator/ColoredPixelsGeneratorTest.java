package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.ColoredPixelsGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ColoredPixelsGeneratorTest {
    private final int[] sizes = new int[]{1, 2, 63, 512};

    @Test(expected = IllegalArgumentException.class)
    public void negativeMultiplierException() {
        new ColoredPixelsGenerator(-12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroMultiplierException() {
        new ColoredPixelsGenerator(0);
    }

    @Test
    public void png() {
        test(1, Quality.png());
        test(10, Quality.png());
        test(200, Quality.png());
    }

    @Test
    public void jpg() {
        test(1, Quality.jpg(80));
        test(10, Quality.jpg(80));
        test(200, Quality.jpg(80));
    }

    @Test
    public void webp() {
        test(1, new Quality(Bitmap.CompressFormat.WEBP, 100));
        test(10, new Quality(Bitmap.CompressFormat.WEBP, 100));
        test(200, new Quality(Bitmap.CompressFormat.WEBP, 100));
    }

    private void test(int pixelMultiplier, Quality quality) {
        ColoredPixelsGenerator generator = new ColoredPixelsGenerator(pixelMultiplier);
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
