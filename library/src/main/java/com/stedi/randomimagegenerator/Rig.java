package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.generators.Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Rig {
    private final Attributes attrs;

    private Rig(Attributes attrs) {
        this.attrs = attrs;
    }

    public void generate() {
        for (int n = 1; n <= attrs.count; n++) {
            ImageParams imageParams = new ImageParams(0, 0, n, attrs.path, attrs.format);
            Bitmap bitmap = null;

            try {
                bitmap = attrs.generator.generate(imageParams);
                if (attrs.generateCallback != null)
                    attrs.generateCallback.onGenerated(imageParams, bitmap);
            } catch (Exception e) {
                if (attrs.generateCallback != null)
                    attrs.generateCallback.onException(imageParams, e);
            }

            if (attrs.path != null && bitmap != null) {
                try {
                    boolean saved = save(bitmap, attrs.path, attrs.format, attrs.quality);
                    if (!saved)
                        throw new NotSavedException();
                    if (attrs.saveCallback != null)
                        attrs.saveCallback.onSave(bitmap, attrs.path);
                } catch (Exception e) {
                    if (attrs.saveCallback != null)
                        attrs.saveCallback.onException(bitmap, attrs.path, e);
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

    private static class Attributes {
        private Generator generator;
        private NamePolicy namePolicy;
        private GenerateCallback generateCallback;
        private int width, height;
        private int widthFrom, widthTo;
        private int heightFrom, heightTo;
        private int count;
        private File path;
        private Bitmap.CompressFormat format;
        private int quality;
        private SaveCallback saveCallback;
    }

    public static class Builder {
        private Attributes a;

        public Builder() {
            this.a = new Attributes();
        }

        public Builder generator(Generator generator) {
            a.generator = generator;
            return this;
        }

        public Builder namePolicy(NamePolicy namePolicy) {
            a.namePolicy = namePolicy;
            return this;
        }

        public Builder generateCallback(GenerateCallback generateCallback) {
            a.generateCallback = generateCallback;
            return this;
        }

        public Builder fixedSize(int width, int height) {
            a.width = width;
            a.height = height;
            return this;
        }

        public Builder widthRange(int from, int to) {
            a.widthFrom = from;
            a.widthTo = to;
            return this;
        }

        public Builder heightRange(int from, int to) {
            a.heightFrom = from;
            a.heightTo = to;
            return this;
        }

        public Builder count(int count) {
            a.count = count;
            return this;
        }

        public Builder path(String path) {
            a.path = new File(path);
            return this;
        }

        public Builder format(Bitmap.CompressFormat format) {
            a.format = format;
            return this;
        }

        public Builder quality(int quality) {
            a.quality = quality;
            return this;
        }

        public Builder saveCallback(SaveCallback saveCallback) {
            a.saveCallback = saveCallback;
            return this;
        }

        public Rig build() {
            return new Rig(a);
        }
    }
}
