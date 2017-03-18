package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertNotNull;

public class BuilderTest {
    private final SaveCallback emptySaveCallback = new SaveCallback() {
        @Override
        public void onSaved(Bitmap bitmap, File file) {

        }

        @Override
        public void onFailedToSave(Bitmap bitmap, File file, Exception e) {

        }
    };

    private final GenerateCallback emptyGenerateCallback = new GenerateCallback() {
        @Override
        public void onGenerated(ImageParams imageParams, Bitmap bitmap) {

        }

        @Override
        public void onFailedToGenerate(ImageParams imageParams, Exception e) {

        }
    };

    @Test
    public void buildFixedSize() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFixedWidthHeight() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(10)
                .setFixedHeight(10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFixedSizeWithOverride() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(20, 20)
                .setFixedWidth(10)
                .setFixedHeight(10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFixedWidthRangeHeight() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(10)
                .setHeightRange(10, 100, 10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFixedHeightRangeWidth() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedHeight(10)
                .setWidthRange(10, 100, 10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildRangeWidthHeight() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setWidthRange(10, 100, 10)
                .setHeightRange(100, 10, 10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFixedSizeWithRangeOverride() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(20, 20)
                .setWidthRange(10, 100, 10)
                .setHeightRange(100, 10, 10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildRangeWithFixedSizeOverride() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setWidthRange(10, 100, 10)
                .setHeightRange(100, 10, 10)
                .setFixedSize(20, 20)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFixedSizeCount() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 10)
                .setCount(10)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildCountThenRange() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setCount(10)
                .setWidthRange(10, 100, 10)
                .setHeightRange(100, 10, 20)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildRangeWithFixedSizeOverrideAndCount() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setWidthRange(10, 100, 10)
                .setHeightRange(100, 10, 20)
                .setFixedSize(10, 100)
                .setCount(5)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFullWithCallback() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 100)
                .setCount(2)
                .setCallback(emptyGenerateCallback)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFullWithQuality() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 100)
                .setCount(2)
                .setCallback(emptyGenerateCallback)
                .setQuality(Quality.png())
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFullWithSavePath() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 100)
                .setCount(2)
                .setCallback(emptyGenerateCallback)
                .setQuality(Quality.png())
                .setFileSavePath("BuilderTest")
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFullWithSaveCallack() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 100)
                .setCount(2)
                .setCallback(emptyGenerateCallback)
                .setQuality(Quality.png())
                .setFileSavePath("BuilderTest")
                .setFileSaveCallback(emptySaveCallback)
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFullWithFileNamePolicy() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 100)
                .setCount(2)
                .setCallback(emptyGenerateCallback)
                .setQuality(Quality.png())
                .setFileSavePath("BuilderTest")
                .setFileSaveCallback(emptySaveCallback)
                .setFileNamePolicy(new DefaultFileNamePolicy())
                .build();

        assertNotNull(rig);
    }

    @Test
    public void buildFullWithRangeOverride() {
        Rig rig = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(10, 100)
                .setCount(2)
                .setCallback(emptyGenerateCallback)
                .setQuality(Quality.png())
                .setFileSavePath("BuilderTest")
                .setFileSaveCallback(emptySaveCallback)
                .setFileNamePolicy(new DefaultFileNamePolicy())
                .setHeightRange(100, 1000, 10)
                .setWidthRange(50, 100, 25)
                .build();

        assertNotNull(rig);
    }
}
