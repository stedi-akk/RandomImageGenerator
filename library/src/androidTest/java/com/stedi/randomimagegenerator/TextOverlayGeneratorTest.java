package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.ColoredCirclesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredNoiseGenerator;
import com.stedi.randomimagegenerator.generators.ColoredPixelsGenerator;
import com.stedi.randomimagegenerator.generators.ColoredRectangleGenerator;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.Generator;
import com.stedi.randomimagegenerator.generators.TextOverlayGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TextOverlayGeneratorTest {
    private final int[] sizes = new int[]{1, 2, 63, 256};

    private final TextOverlayGenerator.TextPolicy textPolicy = new TextOverlayGenerator.TextPolicy() {
        @Override
        public String getText(ImageParams imageParams) {
            return "text";
        }
    };

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
        testThroughBuilder(new FlatColorGenerator(), quality);
        testThroughBuilder(new ColoredRectangleGenerator(), quality);
        testThroughBuilder(new ColoredPixelsGenerator(), quality);
        testThroughBuilder(new ColoredNoiseGenerator(), quality);
        testThroughBuilder(new ColoredCirclesGenerator(), quality);
    }

    private void testThroughBuilder(Generator target, Quality quality) {
        test(new TextOverlayGenerator.Builder()
                .setGenerator(target)
                .build(), quality);

        test(new TextOverlayGenerator.Builder()
                .setGenerator(target)
                .setTextPolicy(textPolicy)
                .setBackgroundPaint(new Paint())
                .setTextPaint(new Paint())
                .setAutoresizeText(true)
                .setDrawBackground(true)
                .build(), quality);

        test(new TextOverlayGenerator.Builder()
                .setGenerator(target)
                .setTextPolicy(null)
                .setBackgroundPaint(null)
                .setTextPaint(null)
                .setAutoresizeText(false)
                .setDrawBackground(false)
                .build(), quality);
    }

    private void test(TextOverlayGenerator generator, Quality quality) {
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
