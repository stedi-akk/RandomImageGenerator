package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import static org.junit.Assert.*;
import org.junit.Test;

public class DefaultFileNamePolicyTest {
    @Test
    public void jpg100x100() {
        ImageParams imageParams = new ImageParams(1, 100, 100, null, Quality.jpg(100), RigPalette.allColors());

        DefaultFileNamePolicy namePolicy = new DefaultFileNamePolicy();
        String name = namePolicy.getName(imageParams);

        assertNotNull(name);
        assertEquals(name, "rig_100x100_1.jpg");
    }

    @Test
    public void png1080x1920() {
        ImageParams imageParams = new ImageParams(10, 1080, 1920, null, Quality.png(), RigPalette.allColors());

        DefaultFileNamePolicy namePolicy = new DefaultFileNamePolicy();
        String name = namePolicy.getName(imageParams);

        assertNotNull(name);
        assertEquals(name, "rig_1080x1920_10.png");
    }

    @Test
    public void webp2033x10() {
        ImageParams imageParams = new ImageParams(1337, 2033, 10, null, new Quality(Bitmap.CompressFormat.WEBP, 100), RigPalette.allColors());

        DefaultFileNamePolicy namePolicy = new DefaultFileNamePolicy();
        String name = namePolicy.getName(imageParams);

        assertNotNull(name);
        assertEquals(name, "rig_2033x10_1337.webp");
    }
}
