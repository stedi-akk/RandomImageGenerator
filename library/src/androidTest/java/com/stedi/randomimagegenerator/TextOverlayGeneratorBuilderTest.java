package com.stedi.randomimagegenerator;

import android.graphics.Paint;
import android.support.test.runner.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.TextOverlayGenerator;

import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TextOverlayGeneratorBuilderTest {
    private final TextOverlayGenerator.TextPolicy textPolicy = new TextOverlayGenerator.TextPolicy() {
        @Override
        public String getText(ImageParams imageParams) {
            return "text";
        }
    };

    @Test(expected = IllegalStateException.class)
    public void buildExceptionWithoutParams() {
        new TextOverlayGenerator.Builder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullGenerator() {
        new TextOverlayGenerator.Builder()
                .setGenerator(null);
    }

    @Test
    public void buildSimple() {
        TextOverlayGenerator generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithTextPolicy() {
        TextOverlayGenerator generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithNullPaint() {
        TextOverlayGenerator generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .setTextPaint(null)
                .setBackgroundPaint(null)
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithNonNullPaint() {
        TextOverlayGenerator generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .setTextPaint(new Paint())
                .setBackgroundPaint(new Paint())
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithFalseSetters() {
        TextOverlayGenerator generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .setTextPaint(new Paint())
                .setBackgroundPaint(new Paint())
                .setDrawBackground(false)
                .setAutoresizeText(false)
                .build();

        assertNotNull(generator);
    }
}
