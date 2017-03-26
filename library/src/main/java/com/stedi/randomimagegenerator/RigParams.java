package com.stedi.randomimagegenerator;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.generators.Generator;

import java.io.File;

/**
 * Generation parameters created by {@link Rig.Builder} and used by {@link Rig}
 */
class RigParams {
    /**
     * Used for bitmap generation.
     * <p>
     * Must not be null.
     * </p>
     */
    Generator generator;

    /**
     * Callbacks while generation.
     * <p>
     * Can be null.
     * </p>
     */
    GenerateCallback generateCallback;

    /**
     * Used by {@link Generator} and for image saving quality
     * <p>
     * Must not be null.
     * </p>
     */
    Quality quality;

    /**
     * Width of generated image.
     * <p>
     * Must be > 0, if width range is not specified.
     * </p>
     */
    int width;

    /**
     * Height of generated image.
     * <p>
     * Must be > 0, if height range is not specified.
     * </p>
     */
    int height;

    /**
     * Width range start. Used along with widthTo and widthStep.
     * <p>
     * Must be > 0, if range is specified.
     * </p>
     */
    int widthFrom;

    /**
     * Width range end. Used along with widthFrom and widthStep.
     * <p>
     * Must be > 0, if range is specified.
     * </p>
     */
    int widthTo;

    /**
     * Width range step. Used along with widthFrom and widthTo.
     * <p>
     * Must be > 0, if range is specified.
     * </p>
     */
    int widthStep;

    /**
     * Height range start. Used along with heightTo and heightStep.
     * <p>
     * Must be > 0, if range is specified.
     * </p>
     */
    int heightFrom;

    /**
     * Height range end. Used along with heightFrom and heightStep.
     * <p>
     * Must be > 0, if range is specified.
     * </p>
     */
    int heightTo;

    /**
     * Height range step. Used along with heightFrom and heightTo.
     * <p>
     * Must be > 0, if range is specified.
     * </p>
     */
    int heightStep;

    /**
     * true if width range is specified.
     */
    boolean useWidthRange;

    /**
     * true if height range is specified.
     */
    boolean useHeightRange;

    /**
     * The number of generated images.
     * <p>
     * Must be > 0 for fixed size.
     * </p>
     */
    int count;

    /**
     * The path where images will be saved.
     * <p>
     * Can be null.
     * </p>
     */
    File path;

    /**
     * File name used for image save.
     * <p>
     * Must not be null if path is specified.
     * </p>
     */
    FileNamePolicy fileNamePolicy;

    /**
     * Callbacks while image save.
     * <p>
     * Can be null.
     * </p>
     */
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
