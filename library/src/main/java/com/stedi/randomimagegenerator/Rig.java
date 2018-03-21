package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
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

/**
 * Random Image Generator (RIG).
 */
public final class Rig {
    private static boolean DEBUG = false;
    private static String TAG = "RIG";

    final RigParams params = new RigParams();

    private volatile boolean isCanceled;

    private int imageId;

    private Rig() {
    }

    /**
     * To start images generation.
     */
    @WorkerThread
    public void generate() {
        if (DEBUG) {
            Log.d(TAG, "Started with parameters: " + params);
        }

        if (DEBUG && params.generateCallback == null && params.path == null) {
            Log.d(TAG, "Started without callback or specified path to save! It looks useless...");
        }

        isCanceled = false;
        imageId = 0;

        if (params.useWidthRange) {
            for (int width : params.widthRangeValues) {
                if (params.useHeightRange) {
                    for (int height : params.heightRangeValues) {
                        generate(width, height, 1);
                    }
                } else {
                    generate(width, params.height, 1);
                }
            }
        } else if (params.useHeightRange) {
            for (int height : params.heightRangeValues) {
                generate(params.width, height, 1);
            }
        } else {
            generate(params.width, params.height, params.count);
        }

        if (DEBUG) {
            if (isCanceled) {
                Log.d(TAG, "Canceled");
            } else {
                Log.d(TAG, "Ended");
            }
        }
    }

    private void generate(int width, int height, int count) {
        for (int i = 0; i < count; i++) {
            if (isCanceled) {
                return;
            }

            ImageParams imageParams = new ImageParams(++imageId, width, height, params.path, params.quality, params.palette, params.fileNamePolicy);

            if (DEBUG) {
                Log.d(TAG, "Created new ImageParams object for generation: " + imageParams);
            }

            Bitmap bitmap = null;
            try {
                if (DEBUG) {
                    Log.d(TAG, "Generating for id " + imageParams.getId());
                }

                bitmap = params.generator.generate(imageParams);
                if (bitmap == null) {
                    throw new NotGeneratedException("Generator return null for id " + imageParams.getId());
                }

                if (DEBUG) {
                    Log.d(TAG, "Successfully generated");
                }

                notifyGenerateCallback(imageParams, bitmap, null);
            } catch (Exception e) {
                if (DEBUG) {
                    Log.d(TAG, "Failed to generate", e);
                }

                notifyGenerateCallback(imageParams, null, e);
            }

            if (params.path != null && bitmap != null) {
                try {
                    String fileName = params.fileNamePolicy.getName(imageParams);
                    if (TextUtils.isEmpty(fileName)) {
                        throw new NotSavedException("File name from FileNamePolicy is not valid for id " + imageParams.getId());
                    }

                    File file = new File(params.path, fileName);
                    Bitmap.CompressFormat compressFormat = params.quality.getFormat();
                    int quality = params.quality.getQualityValue();

                    if (DEBUG) {
                        Log.d(TAG, "Saving generated image " + file.getPath());
                    }

                    if (!save(bitmap, file, compressFormat, quality)) {
                        throw new NotSavedException("Failed to save for id " + imageParams.getId());
                    }

                    if (DEBUG) {
                        Log.d(TAG, "Successfully saved");
                    }

                    notifySaveCallback(bitmap, file, null);
                } catch (Exception e) {
                    if (DEBUG) {
                        Log.d(TAG, "Failed to save", e);
                    }

                    notifySaveCallback(bitmap, null, e);
                }
            }
        }
    }

    /**
     * To save bitmap into file with specified compress format.
     */
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

    /**
     * To notify {@link GenerateCallback} (if it was set with {@link Rig.Builder}).
     *
     * @param imageParams The image parameters that were used for this generation.
     * @param bitmap      The generated image.
     * @param e           If not null, then {@link GenerateCallback#onFailedToGenerate(ImageParams, Exception)} will be called.
     */
    private void notifyGenerateCallback(ImageParams imageParams, Bitmap bitmap, Exception e) {
        if (params.generateCallback != null) {
            try {
                if (e != null) {
                    params.generateCallback.onFailedToGenerate(imageParams, e);
                } else {
                    params.generateCallback.onGenerated(imageParams, bitmap);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * To notify {@link SaveCallback} (if it was set with {@link Rig.Builder}).
     *
     * @param bitmap The generated image.
     * @param path   The path where image was saved.
     * @param e      If not null, then {@link SaveCallback#onFailedToSave(Bitmap, Exception)} will be called.
     */
    private void notifySaveCallback(Bitmap bitmap, File path, Exception e) {
        if (params.saveCallback != null) {
            try {
                if (e != null) {
                    params.saveCallback.onFailedToSave(bitmap, e);
                } else {
                    params.saveCallback.onSaved(bitmap, path);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * To cancel ongoing generation.
     */
    public void cancel() {
        isCanceled = true;
    }

    /**
     * @return Number of images to be generated.
     */
    public int getCount() {
        if (!params.useWidthRange && !params.useHeightRange) {
            return params.count;
        }

        if (params.useWidthRange) {
            int widthRangeLength = params.widthRangeValues.length;
            return params.useHeightRange ? widthRangeLength * params.heightRangeValues.length : widthRangeLength;
        } else {
            return params.heightRangeValues.length;
        }
    }

    /**
     * To show logcat logs while generation (with tag {@link Rig#TAG}).
     */
    public static void enableDebugLogging(boolean enable) {
        DEBUG = enable;
    }

    /**
     * Creates a positive range array. For example, if from=200, to=800, step=200, then
     * a new array [200, 400, 600, 800] will be returned.
     *
     * @param from Must be bigger than 0. Always present in the return array as the first item.
     * @param to   Must be bigger than 0. Always present in the return array as the last item.
     * @param step Must be bigger than 0.
     */
    @NonNull
    public static int[] createRangeArray(int from, int to, int step) {
        if (step <= 0 || from <= 0 || to <= 0) {
            throw new IllegalArgumentException("all args must be bigger than 0");
        }

        boolean isDecreasing = to < from;
        int size = (int) Math.ceil(Math.abs((1f * to - from) / step * 1f));
        if (size == 0) {
            size = 1;
        }

        int[] array = new int[size + 1];
        array[array.length - 1] = to;

        int value = from;
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = value;
            if (isDecreasing) {
                value -= step;
            } else {
                value += step;
            }
        }

        return array;
    }

    /**
     * Returns a pseudorandom {@code boolean}.
     */
    public static boolean random() {
        return Math.random() < 0.5;
    }

    /**
     * Returns a pseudorandom positive {@code float} value between 0 and the specified value (excluded).
     */
    public static float random(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be positive");
        }

        return (float) (Math.random() * value);
    }

    /**
     * Returns a pseudorandom positive {@code float} value between the specified positive range.
     *
     * @param from Start of the range (included).
     * @param to   End of the range (excluded).
     */
    public static float random(float from, float to) {
        if (from < 0 || to < 0) {
            throw new IllegalArgumentException("range must be positive");
        }

        if (from == to) {
            return from;
        }

        if (to < from) {
            float temp = from;
            from = to;
            to = temp;
        }

        return (float) (Math.random() * (to - from) + from);
    }

    /**
     * Builder that is used to create an instance of {@link Rig}.
     */
    public static class Builder {
        private final RigParams p;

        public Builder() {
            this.p = new RigParams();
        }

        /**
         * Set generator that will be used for images generation.
         * <p>Must not be null.</p>
         *
         * @param generator Interface definition for a generator, that is used to generate images.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setGenerator(@NonNull Generator generator) {
            if (generator == null) {
                throw new IllegalArgumentException("generator cannot be null");
            }
            p.generator = generator;
            return this;
        }

        /**
         * Set palette which will be used for images generation.
         * <p>If not specified, then the default palette will be used.</p>
         *
         * @param palette Color palette for images generation.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setPalette(@Nullable RigPalette palette) {
            p.palette = palette;
            return this;
        }

        /**
         * Set callback that will be used to inform if image was generated or not.
         *
         * @param generateCallback Interface definition for a callback to be invoked when an image is generated or not.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setCallback(@Nullable GenerateCallback generateCallback) {
            p.generateCallback = generateCallback;
            return this;
        }

        /**
         * Set quality that will be used for images generation and compression.
         * <p>If not specified, then the default quality (PNG) will be used.</p>
         *
         * @param quality A specified quality to use for bitmaps generation and compression.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setQuality(@Nullable Quality quality) {
            p.quality = quality;
            return this;
        }

        /**
         * Set fixed size for generated images.
         * <p>Will override range sizes (if were specified).</p>
         *
         * @param width  Must be bigger than 0.
         * @param height Must be bigger than 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setFixedSize(int width, int height) {
            return setFixedWidth(width).setFixedHeight(height);
        }

        /**
         * Set fixed width for generated images.
         * <p>Will override width range size (if was specified).</p>
         *
         * @param width Must be bigger than 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setFixedWidth(int width) {
            if (width <= 0) {
                throw new IllegalArgumentException("width must be bigger than 0");
            }
            p.width = width;
            p.useWidthRange = false;
            return this;
        }

        /**
         * Set fixed height for generated images.
         * <p>Will override height range size (if was specified).</p>
         *
         * @param height Must be bigger than 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setFixedHeight(int height) {
            if (height <= 0) {
                throw new IllegalArgumentException("height must be bigger than 0");
            }
            p.height = height;
            p.useHeightRange = false;
            return this;
        }

        /**
         * Set width range for generated images.
         * <p>For example, if from=200, to=800, step=200, then it will turn into
         * an array [200, 400, 600, 800].</p>
         * <p>Will override fixed width (if was specified).</p>
         * <p>Count will be ignored (if was specified).</p>
         *
         * @param from Start width in array. Must be bigger than 0.
         * @param to   End width in array. Must be bigger than 0.
         * @param step Step width in array. Must be bigger than 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setWidthRange(int from, int to, int step) {
            if (step <= 0 || from <= 0 || to <= 0) {
                throw new IllegalArgumentException("all width range args must be bigger than 0");
            }
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
         * <p>Will override fixed height (if was specified).</p>
         * <p>Count will be ignored (if was specified).</p>
         *
         * @param from Start height in array. Must be bigger than 0.
         * @param to   End height in array. Must be bigger than 0.
         * @param step Step height in array. Must be bigger than 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setHeightRange(int from, int to, int step) {
            if (step <= 0 || from <= 0 || to <= 0) {
                throw new IllegalArgumentException("all height range args must be bigger than 0");
            }
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
         * @param count Must be bigger than 0.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setCount(int count) {
            if (p.useWidthRange || p.useHeightRange) {
                throw new IllegalStateException("count can not be set with size range");
            }
            if (count <= 0) {
                throw new IllegalArgumentException("count must be bigger than 0");
            }
            p.count = count;
            return this;
        }

        /**
         * File path (directories) that will be used to save generated images.
         * <p>Will be created if not exists.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setFileSavePath(@Nullable String path) {
            if (path == null) {
                p.path = null;
                return this;
            }
            File f = new File(path);
            boolean exists = f.exists();
            if (!exists) {
                exists = f.mkdirs();
            }
            if (!exists) {
                throw new IllegalArgumentException("path is not valid");
            }
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
        @NonNull
        public Builder setFileNamePolicy(@Nullable FileNamePolicy fileNamePolicy) {
            p.fileNamePolicy = fileNamePolicy;
            return this;
        }

        /**
         * Set callback that will be used to inform if image was saved, or not.
         *
         * @param saveCallback Interface definition for a callback to be invoked when an image is saved.
         * @return This Builder object to allow for chaining of calls to set methods.
         */
        @NonNull
        public Builder setFileSaveCallback(@Nullable SaveCallback saveCallback) {
            p.saveCallback = saveCallback;
            return this;
        }

        /**
         * Creates an {@link Rig} instance with the arguments supplied to this
         * builder.
         *
         * @return A new instance of {@link Rig}.
         */
        @NonNull
        public Rig build() {
            if (p.generator == null) {
                throw new IllegalStateException("generator not specified");
            }
            if (!p.useHeightRange && p.height == 0) {
                throw new IllegalStateException("height not specified");
            }
            if (!p.useWidthRange && p.width == 0) {
                throw new IllegalStateException("width not specified");
            }
            if ((p.fileNamePolicy != null || p.saveCallback != null) && p.path == null) {
                throw new IllegalStateException("path not specified");
            }
            if (p.useWidthRange) {
                p.widthRangeValues = createRangeArray(p.widthFrom, p.widthTo, p.widthStep);
            }
            if (p.useHeightRange) {
                p.heightRangeValues = createRangeArray(p.heightFrom, p.heightTo, p.heightStep);
            }
            if (p.palette == null) {
                p.palette = RigPalette.allColors();
            }
            if (p.quality == null) {
                p.quality = Quality.png();
            }
            if (p.count == 0) {
                p.count = 1;
            }
            if (p.path != null && p.fileNamePolicy == null) {
                p.fileNamePolicy = new DefaultFileNamePolicy();
            }
            Rig rig = new Rig();
            rig.params.apply(p);
            return rig;
        }
    }
}
