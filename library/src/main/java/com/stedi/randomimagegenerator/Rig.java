package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.text.TextUtils;
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

    final RigParams params = new RigParams();

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
                try {
                    String fileName = params.fileNamePolicy.getName(imageParams);
                    if (TextUtils.isEmpty(fileName))
                        throw new NotSavedException("File name from FileNamePolicy is not valid for id " + imageParams.getId());
                    File file = new File(params.path, fileName);
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
                    notifySaveCallback(bitmap, null, e);
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
                params.saveCallback.onFailedToSave(bitmap, e);
            else
                params.saveCallback.onSaved(bitmap, path);
        }
    }

    public static void enableDebugLogging(boolean enable) {
        DEBUG = enable;
    }

    /**
     * Builder that is used to create an instance of {@code Rig}.
     */
    public static class Builder {
        private final RigParams p;

        public Builder() {
            this.p = new RigParams();
        }

        /**
         * Set generator that will be used for image generation.
         * <p>Must not be null.</p>
         *
         * @param generator Interface definition for a generator, that is used to generate an image.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setGenerator(Generator generator) {
            if (generator == null)
                throw new IllegalArgumentException("generator cannot be null");
            p.generator = generator;
            return this;
        }

        /**
         * Set callback that will be used to inform if image is generated or not.
         *
         * @param generateCallback Interface definition for a callback to be invoked when an image is generated or not.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setCallback(GenerateCallback generateCallback) {
            p.generateCallback = generateCallback;
            return this;
        }

        /**
         * Set quality that will be used for image generation and compression.
         * <p>If not specified, then the default quality (PNG) will be used.</p>
         *
         * @param quality A specified quality to use for bitmap generation and compression.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setQuality(Quality quality) {
            p.quality = quality;
            return this;
        }

        /**
         * Set fixed size for generated images.
         * <p>Will override range sizes (if they was specified).</p>
         *
         * @param width  Must be > 0.
         * @param height Must be > 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setFixedSize(int width, int height) {
            return setFixedWidth(width).setFixedHeight(height);
        }

        /**
         * Set fixed width for generated images.
         * <p>Will override width range size (if it was specified).</p>
         *
         * @param width Must be > 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setFixedWidth(int width) {
            if (width <= 0)
                throw new IllegalArgumentException("width must be > 0");
            p.width = width;
            p.useWidthRange = false;
            return this;
        }

        /**
         * Set fixed height for generated images.
         * <p>Will override height range size (if it was specified).</p>
         *
         * @param height Must be > 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setFixedHeight(int height) {
            if (height <= 0)
                throw new IllegalArgumentException("height must be > 0");
            p.height = height;
            p.useHeightRange = false;
            return this;
        }

        /**
         * Set width range for generated images.
         * <p>For example, if from=200, to=800, step=200, then it will turn into
         * an array [200, 400, 600, 800].</p>
         * <p>Will override fixed width (if it was specified).</p>
         * <p>Count will be ignored (if it was specified).</p>
         *
         * @param from Start width in array. Must be > 0.
         * @param to   End width in array. Must be > 0.
         * @param step Step width in array. Must be > 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
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

        /**
         * Set height range for generated images.
         * <p>For example, if from=800, to=200, step=200, then it will turn into
         * an array [800, 600, 400, 200].</p>
         * <p>Will override fixed height (if it was specified).</p>
         * <p>Count will be ignored (if it was specified).</p>
         *
         * @param from Start height in array. Must be > 0.
         * @param to   End height in array. Must be > 0.
         * @param step Step height in array. Must be > 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
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

        /**
         * Set count of generated images.
         * <p>Must not be used with range sizes.</p>
         * <p>If not specified, then it will be set to 1.</p>
         *
         * @param count Must be > 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setCount(int count) {
            if (p.useWidthRange || p.useHeightRange)
                throw new IllegalStateException("count can not be set with size range");
            if (count <= 0)
                throw new IllegalArgumentException("count must be > 0");
            p.count = count;
            return this;
        }

        /**
         * File path (without filename) that will be used to save generated images.
         * <p>The path (directories) will be created if not exists.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods.
         */
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

        /**
         * Set file name policy that will be used for saving images.
         * <p>If not specified, then the {@link DefaultFileNamePolicy} will be used.</p>
         *
         * @param fileNamePolicy Used to save images with the corresponding names retrieved from it.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setFileNamePolicy(FileNamePolicy fileNamePolicy) {
            p.fileNamePolicy = fileNamePolicy;
            return this;
        }

        /**
         * Set callback that will be used to inform if image is saved, or not.
         *
         * @param saveCallback Interface definition for a callback to be invoked when an image is saved.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        public Builder setFileSaveCallback(SaveCallback saveCallback) {
            p.saveCallback = saveCallback;
            return this;
        }

        /**
         * Creates an {@link Rig} object with the arguments supplied to this
         * builder.
         *
         * @return A new instance of {@link Rig}.
         */
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
