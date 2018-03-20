package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;

import java.util.List;

/**
 * Generator which holds a pack of generators, that is used to pick random generator to generate next image.
 */
public class GeneratorPack implements Generator {
    private List<Generator> pack;

    public GeneratorPack() {
    }

    public GeneratorPack(@NonNull List<Generator> pack) {
        this.pack = pack;
    }

    public void set(@NonNull List<Generator> pack) {
        this.pack = pack;
    }

    @Nullable
    @Override
    @WorkerThread
    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
        return pack.get((int) Rig.random(pack.size())).generate(imageParams);
    }

    @Override
    public String toString() {
        return "GeneratorPack{" +
                "pack=" + pack +
                '}';
    }
}
