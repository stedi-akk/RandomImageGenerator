package com.stedi.randomimagegenerator;

import java.util.Locale;

public class DefaultFileNamePolicy implements FileNamePolicy {
    private final String format = "rig_%dx%d_%d.%s";

    @Override
    public String getName(ImageParams imageParams) {
        return String.format(Locale.getDefault(), format,
                imageParams.getWidth(), imageParams.getHeight(),
                imageParams.getId(), imageParams.getQuality().getFileExtension());
    }

    @Override
    public String toString() {
        return "DefaultFileNamePolicy{" +
                "format='" + format + '\'' +
                '}';
    }
}
