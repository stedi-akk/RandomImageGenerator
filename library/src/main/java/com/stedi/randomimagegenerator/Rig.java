package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.util.Log;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.exceptions.NotGeneratedException;
import com.stedi.randomimagegenerator.exceptions.NotSavedException;
import com.stedi.randomimagegenerator.generators.Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public final class Rig {
    private static boolean DEBUG = false;
    private static String TAG = "RIG";

    private final RigParams params = new RigParams();

    private int imageId;

    private Rig() {
    }

    public void generate() {
        if (DEBUG)
            Log.d(TAG, "Started with parameters: " + params);

        if (DEBUG && params.generateCallback == null && params.path == null)
            Log.d(TAG, "Started without callback or specified path to save ! It looks useless...");

        int[] widthValues = null;
        int[] heightValues = null;
        if (params.useWidthRange) {
            widthValues = createRangeArray(params.widthFrom, params.widthTo, params.widthStep);
            if (DEBUG)
                Log.d(TAG, "Created an array for width range: " + Arrays.toString(widthValues));
        }
        if (params.useHeightRange) {
            heightValues = createRangeArray(params.heightFrom, params.heightTo, params.heightStep);
            if (DEBUG)
                Log.d(TAG, "Created an array for height range: " + Arrays.toString(heightValues));
        }

        if (widthValues != null) {
            for (int width : widthValues) {
                if (heightValues != null) {
                    for (int height : heightValues) {
                        generate(width, height, 1);
                    }
                } else {
                    generate(width, params.height, 1);
                }
            }
        } else if (heightValues != null) {
            for (int height : heightValues) {
                generate(params.width, height, 1);
            }
        } else {
            generate(params.width, params.height, params.count);
        }

        if (DEBUG)
            Log.d(TAG, "Ended");
    }

    private void generate(int width, int height, int count) {
        for (int i = 0; i < count; i++) {
            ImageParams imageParams = new ImageParams(++imageId, width, height, params.path, params.quality);
            if (DEBUG)
                Log.d(TAG, "Created new ImageParams object for generation: " + imageParams);

            Bitmap bitmap = null;
            try {
                if (DEBUG)
                    Log.d(TAG, "Generating for id " + imageParams.getId());
                bitmap = params.generator.generate(imageParams);
                if (bitmap == null)
                    throw new NotGeneratedException("Generator return null for id " + imageParams.getId());
                if (DEBUG)
                    Log.d(TAG, "Successfully generated");
                notifyCallback(imageParams, bitmap, null);
            } catch (Exception e) {
                if (DEBUG)
                    Log.d(TAG, "Failed to generate", e);
                notifyCallback(imageParams, null, e);
            }

            if (params.path != null && bitmap != null) {
                File file = new File(params.path, params.fileNamePolicy.getName(imageParams));
                try {
                    Bitmap.CompressFormat compressFormat = params.quality.getFormat();
                    int quality = params.quality.getQualityValue();
                    if (DEBUG)
                        Log.d(TAG, "Saving generated image " + file.getPath());
                    if (!save(bitmap, file, compressFormat, quality))
                        throw new NotSavedException("Failed to save for id " + imageParams.getId());
                    if (DEBUG)
                        Log.d(TAG, "Successfully saved");
                    notifySaveCallback(bitmap, file, null);
                } catch (Exception e) {
                    if (DEBUG)
                        Log.d(TAG, "Failed to save", e);
                    notifySaveCallback(bitmap, file, e);
                }
            }
        }
    }

    private boolean save(Bitmap bitmap, File file, Bitmap.CompressFormat format, int quality) throws Exception {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            return bitmap.compress(format, quality, out);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int[] createRangeArray(int from, int to, int step) {
        boolean isDecreasing = to < from;
        int size = Math.abs((to - from) / step);
        int[] array = new int[size + 1];
        int value = from;
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
            if (isDecreasing)
                value -= step;
            else
                value += step;
        }
        return array;
    }

    private void notifyCallback(ImageParams imageParams, Bitmap bitmap, Exception e) {
        if (params.generateCallback != null) {
            if (e != null)
                params.generateCallback.onFailedToGenerate(imageParams, e);
            else
                params.generateCallback.onGenerated(imageParams, bitmap);
        }
    }

    private void notifySaveCallback(Bitmap bitmap, File path, Exception e) {
        if (params.saveCallback != null) {
            if (e != null)
                params.saveCallback.onFailedToSave(bitmap, path, e);
            else
                params.saveCallback.onSaved(bitmap, path);
        }
    }

    public static void enableDebugLogging(boolean enable) {
        DEBUG = enable;
    }

    private static class RigParams {
        private Generator generator;
        private GenerateCallback generateCallback;
        private Quality quality;
        private int width, height;
        private int widthFrom, widthTo, widthStep;
        private int heightFrom, heightTo, heightStep;
        private boolean useWidthRange;
        private boolean useHeightRange;
        private int count;
        private File path;
        private FileNamePolicy fileNamePolicy;
        private SaveCallback saveCallback;

        private void apply(RigParams params) {
            generator = params.generator;
            generateCallback = params.generateCallback;
            quality = params.quality;
            width = params.width;
            height = params.height;
            widthFrom = params.widthFrom;
            widthTo = params.widthTo;
            widthStep = params.widthStep;
            heightFrom = params.heightFrom;
            heightTo = params.heightTo;
            heightStep = params.heightStep;
            useWidthRange = params.useWidthRange;
            useHeightRange = params.useHeightRange;
            count = params.count;
            path = params.path;
            fileNamePolicy = params.fileNamePolicy;
            saveCallback = params.saveCallback;
        }

        @Override
        public String toString() {
            return "RigParams{" +
                    "generator=" + generator +
                    ", generateCallback=" + generateCallback +
                    ", quality=" + quality +
                    ", width=" + width +
                    ", height=" + height +
                    ", widthFrom=" + widthFrom +
                    ", widthTo=" + widthTo +
                    ", widthStep=" + widthStep +
                    ", heightFrom=" + heightFrom +
                    ", heightTo=" + heightTo +
                    ", heightStep=" + heightStep +
                    ", useWidthRange=" + useWidthRange +
                    ", useHeightRange=" + useHeightRange +
                    ", count=" + count +
                    ", path=" + path +
                    ", fileNamePolicy=" + fileNamePolicy +
                    ", saveCallback=" + saveCallback +
                    '}';
        }
    }

    public static class Builder {
        private final RigParams p;

        public Builder() {
            this.p = new RigParams();
        }

        public Builder setGenerator(Generator generator) {
            if (generator == null)
                throw new IllegalArgumentException("generator cannot be null");
            p.generator = generator;
            return this;
        }

        public Builder setCallback(GenerateCallback generateCallback) {
            p.generateCallback = generateCallback;
            return this;
        }

        public Builder setQuality(Quality quality) {
            p.quality = quality;
            return this;
        }

        public Builder setFixedSize(int width, int height) {
            return setFixedWidth(width).setFixedHeight(height);
        }

        public Builder setFixedWidth(int width) {
            if (width <= 0)
                throw new IllegalArgumentException("width must be > 0");
            p.width = width;
            p.useWidthRange = false;
            return this;
        }

        public Builder setFixedHeight(int height) {
            if (height <= 0)
                throw new IllegalArgumentException("height must be > 0");
            p.height = height;
            p.useHeightRange = false;
            return this;
        }

        public Builder setWidthRange(int from, int to, int step) {
            if (step <= 0 || from <= 0 || to <= 0)
                throw new IllegalArgumentException("all width range args must be > 0");
            p.widthFrom = from;
            p.widthTo = to;
            p.widthStep = step;
            p.useWidthRange = true;
            p.count = 0;
            return this;
        }

        public Builder setHeightRange(int from, int to, int step) {
            if (step <= 0 || from <= 0 || to <= 0)
                throw new IllegalArgumentException("all height range args must be > 0");
            p.heightFrom = from;
            p.heightTo = to;
            p.heightStep = step;
            p.useHeightRange = true;
            p.count = 0;
            return this;
        }

        public Builder setCount(int count) {
            if (p.useWidthRange || p.useHeightRange)
                throw new IllegalStateException("count can not be set with size range");
            if (count <= 0)
                throw new IllegalArgumentException("count must be > 0");
            p.count = count;
            return this;
        }

        public Builder setFileSavePath(String path) {
            if (path == null) {
                p.path = null;
                return this;
            }
            File f = new File(path);
            boolean exists = f.exists();
            if (!exists)
                exists = f.mkdirs();
            if (!exists)
                throw new IllegalArgumentException("path is not valid");
            p.path = f;
            return this;
        }

        public Builder setFileNamePolicy(FileNamePolicy fileNamePolicy) {
            p.fileNamePolicy = fileNamePolicy;
            return this;
        }

        public Builder setFileSaveCallback(SaveCallback saveCallback) {
            p.saveCallback = saveCallback;
            return this;
        }

        public Rig build() {
            if (p.generator == null)
                throw new IllegalStateException("generator not specified");
            if (!p.useHeightRange && p.height == 0)
                throw new IllegalStateException("height not specified");
            if (!p.useWidthRange && p.width == 0)
                throw new IllegalStateException("width not specified");
            if (p.quality == null)
                p.quality = Quality.png();
            if (p.count == 0)
                p.count = 1;
            if ((p.fileNamePolicy != null || p.saveCallback != null) && p.path == null)
                throw new IllegalStateException("path not specified");
            if (p.path != null && p.fileNamePolicy == null)
                p.fileNamePolicy = new DefaultFileNamePolicy();
            Rig rig = new Rig();
            rig.params.apply(p);
            return rig;
        }
    }
}
