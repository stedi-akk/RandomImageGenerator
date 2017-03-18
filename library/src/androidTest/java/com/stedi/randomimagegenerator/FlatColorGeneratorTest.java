package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FlatColorGeneratorTest {
    @Test
    public void png120x360() {
        ImageParams params = new ImageParams(1, 120, 360, null, Quality.png());

        FlatColorGenerator generator = new FlatColorGenerator();
        try {
            Bitmap bitmap = generator.generate(params);
            assertNotNull(bitmap);
            assertEquals(bitmap.getWidth(), 120);
            assertEquals(bitmap.getHeight(), 360);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void jpg1200x36() {
        ImageParams params = new ImageParams(10, 1200, 36, null, Quality.jpg(80));

        FlatColorGenerator generator = new FlatColorGenerator();
        try {
            Bitmap bitmap = generator.generate(params);
            assertNotNull(bitmap);
            assertEquals(bitmap.getWidth(), 1200);
            assertEquals(bitmap.getHeight(), 36);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void webp1x3600() {
        ImageParams params = new ImageParams(3, 1, 3600, null, new Quality(Bitmap.CompressFormat.WEBP, 100));

        FlatColorGenerator generator = new FlatColorGenerator();
        try {
            Bitmap bitmap = generator.generate(params);
            assertNotNull(bitmap);
            assertEquals(bitmap.getWidth(), 1);
            assertEquals(bitmap.getHeight(), 3600);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
