package com.stedi.randomimagegenerator;

import android.support.annotation.NonNull;

import java.util.Locale;

public class DefaultFileNamePolicy implements FileNamePolicy {
    private final String format = "rig_%dx%d_%d.%s";

    @Override
    @NonNull
    public String getName(@NonNull ImageParams imageParams) {
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
