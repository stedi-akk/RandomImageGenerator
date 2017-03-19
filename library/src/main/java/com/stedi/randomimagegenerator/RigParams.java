package com.stedi.randomimagegenerator;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.generators.Generator;

import java.io.File;

class RigParams {
    Generator generator;
    GenerateCallback generateCallback;
    Quality quality;
    int width, height;
    int widthFrom, widthTo, widthStep;
    int heightFrom, heightTo, heightStep;
    boolean useWidthRange;
    boolean useHeightRange;
    int count;
    File path;
    FileNamePolicy fileNamePolicy;
    SaveCallback saveCallback;

    void apply(RigParams from) {
        generator = from.generator;
        generateCallback = from.generateCallback;
        quality = from.quality;
        width = from.width;
        height = from.height;
        widthFrom = from.widthFrom;
        widthTo = from.widthTo;
        widthStep = from.widthStep;
        heightFrom = from.heightFrom;
        heightTo = from.heightTo;
        heightStep = from.heightStep;
        useWidthRange = from.useWidthRange;
        useHeightRange = from.useHeightRange;
        count = from.count;
        path = from.path;
        fileNamePolicy = from.fileNamePolicy;
        saveCallback = from.saveCallback;
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
