package com.stedi.randomimagegenerator;

import com.stedi.randomimagegenerator.generators.Generator;

public class Rig {
    private Rig(Attributes attrs) {

    }

    private static class Attributes {
        private Generator generator;
        private NamePolicy namePolicy;
        private Callback callback;
        private int width, height;
        private int widthFrom, widthTo;
        private int heightFrom, heightTo;
        private int count;
        private String path;
        private Format format;
    }

    public static class Builder {
        private Attributes attrs;

        public Builder() {
            this.attrs = new Attributes();
        }

        public Builder generator(Generator generator) {
            attrs.generator = generator;
            return this;
        }

        public Builder namePolicy(NamePolicy namePolicy) {
            attrs.namePolicy = namePolicy;
            return this;
        }

        public Builder callback(Callback callback) {
            attrs.callback = callback;
            return this;
        }

        public Builder fixedSize(int width, int height) {
            attrs.width = width;
            attrs.height = height;
            return this;
        }

        public Builder widthRange(int from, int to) {
            attrs.widthFrom = from;
            attrs.widthTo = to;
            return this;
        }

        public Builder heightRange(int from, int to) {
            attrs.heightFrom = from;
            attrs.heightTo = to;
            return this;
        }

        public Builder count(int count) {
            attrs.count = count;
            return this;
        }

        public Builder path(String path) {
            attrs.path = path;
            return this;
        }

        public Builder format(Format format) {
            attrs.format = format;
            return this;
        }

        public Rig build() {
            return new Rig(attrs);
        }
    }
}
