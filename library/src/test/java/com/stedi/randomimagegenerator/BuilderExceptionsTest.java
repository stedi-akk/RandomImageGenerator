package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

import org.junit.Test;

import java.io.File;

public class BuilderExceptionsTest {
    private final SaveCallback emptySaveCallback = new SaveCallback() {
        @Override
        public void onSaved(Bitmap bitmap, File file) {

        }

        @Override
        public void onFailedToSave(Bitmap bitmap, Exception e) {

        }
    };

    @Test(expected = IllegalStateException.class)
    public void buildEmpty() {
        new Rig.Builder()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutWidthAndHeight() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutWidth() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedHeight(10)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutHeight() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(10)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithWidthRangeWithoutHeight() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setWidthRange(10, 100, 10)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithHeightRangeWithoutWidth() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setHeightRange(10, 100, 10)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutPathWithCallback() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(10)
                .setFixedHeight(10)
                .setFileSaveCallback(emptySaveCallback)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutPathWithFileNamePolicy() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(10)
                .setFixedHeight(10)
                .setFileNamePolicy(new DefaultFileNamePolicy())
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutPathWithCallbackAndFileNamePolicy() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(10)
                .setFixedHeight(10)
                .setFileSaveCallback(emptySaveCallback)
                .setFileNamePolicy(new DefaultFileNamePolicy())
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullGenerator() {
        new Rig.Builder()
                .setGenerator(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeWidth() {
        new Rig.Builder()
                .setFixedWidth(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZeroWidth() {
        new Rig.Builder()
                .setFixedWidth(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeHeight() {
        new Rig.Builder()
                .setFixedHeight(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZeroHeight() {
        new Rig.Builder()
                .setFixedHeight(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeWidthHeight() {
        new Rig.Builder()
                .setFixedSize(-10, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZeroWidthHeight() {
        new Rig.Builder()
                .setFixedSize(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeCount() {
        new Rig.Builder()
                .setCount(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZeroCount() {
        new Rig.Builder()
                .setCount(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeWidthRange() {
        new Rig.Builder()
                .setWidthRange(-10, -10, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWidthRangeFromZero() {
        new Rig.Builder()
                .setWidthRange(0, 100, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWidthRangeToZero() {
        new Rig.Builder()
                .setWidthRange(100, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeHeightRange() {
        new Rig.Builder()
                .setHeightRange(-10, -10, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHeightRangeFromZero() {
        new Rig.Builder()
                .setHeightRange(0, 100, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHeightRangeToZero() {
        new Rig.Builder()
                .setHeightRange(100, 0, 10);
    }

    @Test(expected = IllegalStateException.class)
    public void setCountAfterRange() {
        new Rig.Builder()
                .setHeightRange(10, 100, 10)
                .setCount(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidSavePath() {
        new Rig.Builder()
                .setFileSavePath("");
    }
}
