package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.ImageParams;

public class NamedGenerator implements Generator {
    private final Generator generator;

    public NamedGenerator(Generator generator) {
        this.generator = generator;
    }

    @Override
    public Bitmap generate(ImageParams imageParams) {
        Bitmap bitmap = generator.generate(imageParams);


        return null;
    }
}
