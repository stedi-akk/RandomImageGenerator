package com.stedi.randomimagegenerator;

import java.util.Locale;

public class DefaultFileNamePolicy implements FileNamePolicy {
    @Override
    public String getName(ImageParams imageParams) {
        return String.format(Locale.getDefault(), "rig_%dx%d_%d.%s",
                imageParams.getWidth(), imageParams.getHeight(),
                imageParams.getId(), imageParams.getQuality().getFileExtension());
    }
}
