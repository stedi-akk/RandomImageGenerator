package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.generators.Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Rig {
    private final Params params;

    private Rig(Params params) {
        this.params = params;
    }

    public void generate() {
        int[] widthValues = null;
        int[] heightValues = null;

        if (params.useWidthRange) {
            widthValues = createRangeArray(params.widthFrom, params.widthTo, params.widthStep);
        }

        if (params.useHeightRange) {
            widthValues = createRangeArray(params.heightFrom, params.heightTo, params.heightStep);
        }

        if (widthValues != null) {
            for (int i = 0; i < widthValues.length; i++) {
                int width = widthValues[i];

                if (heightValues != null) {
                    for (int j = 0; j < heightValues.length; j++) {
                        int height = heightValues[j];
                        generate(width, height, 1);
                    }
                } else {
                    generate(width, params.height, 1);
                }
            }
        } else if (heightValues != null) {
            for (int i = 0; i < heightValues.length; i++) {
                int height = heightValues[i];
                generate(params.width, height, 1);
            }
        } else {
            generate(params.width, params.height, params.count);
        }
    }

    private void generate(int width, int height, int count) {
        for (int n = 1; n <= count; n++) {
            ImageParams imageParams = new ImageParams(n, width, height, params.path, params.quality);

            Bitmap bitmap = null;
            try {
                bitmap = params.generator.generate(imageParams);
                notifyCallback(imageParams, bitmap, null);
            } catch (Exception e) {
                notifyCallback(imageParams, null, e);
            }

            if (params.path != null && bitmap != null) {
                try {
                    Bitmap.CompressFormat compressFormat = params.quality.getFormat();
                    int quality = params.quality.getQualityValue();
                    if (!save(bitmap, params.path, compressFormat, quality))
                        throw new NotSavedException();
                    notifySaveCallback(bitmap, params.path, null);
                } catch (Exception e) {
                    notifySaveCallback(bitmap, params.path, e);
                }
            }
        }
    }

    private boolean save(Bitmap bitmap, File path, Bitmap.CompressFormat format, int quality) throws Exception {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
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

    private void notifyCallback(ImageParams imageParams, Bitmap bitmap, Exception e) {
        if (params.generateCallback != null) {
            if (e != null)
                params.generateCallback.onException(imageParams, e);
            else
                params.generateCallback.onGenerated(imageParams, bitmap);
        }
    }

    private void notifySaveCallback(Bitmap bitmap, File path, Exception e) {
        if (params.saveCallback != null) {
            if (e != null)
                params.saveCallback.onException(bitmap, path, e);
            else
                params.saveCallback.onSaved(bitmap, path);
        }
    }

    private int[] createRangeArray(int from, int to, int step) {
        int size = (to - from) / step;
        int[] array = new int[size + 1];
        int value = from;
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
            value += step;
        }
        return array;
    }

    private static class Params {
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
    }

    public static class Builder {
        private final Params p;

        public Builder() {
            this.p = new Params();
        }

        public Builder setGenerator(Generator generator) {
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
            p.width = width;
            p.useWidthRange = false;
            return this;
        }

        public Builder setFixedHeight(int height) {
            p.height = height;
            p.useHeightRange = false;
            return this;
        }

        public Builder setWidthRange(int from, int to, int step) {
            p.widthFrom = from;
            p.widthTo = to;
            p.useWidthRange = true;
            p.count = 0;
            return this;
        }

        public Builder setHeightRange(int from, int to, int step) {
            p.heightFrom = from;
            p.heightTo = to;
            p.useHeightRange = true;
            p.count = 0;
            return this;
        }

        public Builder setCount(int count) {
            if (p.useWidthRange || p.useHeightRange)
                throw new IllegalStateException("count can not be set with size range");
            p.count = count;
            return this;
        }

        public Builder setFileSavePath(String path) {
            p.path = new File(path);
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
            return new Rig(p);
        }
    }
}
