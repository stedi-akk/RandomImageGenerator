package com.stedi.randomimagegenerator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RigPaletteTest {
    @Test
    public void testAllColors() {
        RigPalette palette = RigPalette.allColors();
        assertTrue(palette.getHueFrom() == 0);
        assertTrue(palette.getHueTo() == 360);
        assertFalse(palette.isBlackAndWhite());
        assertTrue(palette.isUseDarkColors());
        assertTrue(palette.isUseLightColors());
    }

    @Test
    public void testHueRange() {
        RigPalette palette = RigPalette.hueRange(10, 340);
        assertTrue(palette.getHueFrom() == 10);
        assertTrue(palette.getHueTo() == 340);
        assertFalse(palette.isBlackAndWhite());
        assertFalse(palette.isUseDarkColors());
        assertFalse(palette.isUseLightColors());
    }

    @Test
    public void testWrongHue() {
        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueFrom(-10);
            fail();
        } catch (Exception e) {
        }

        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueFrom(400);
            fail();
        } catch (Exception e) {
        }

        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueTo(-10);
            fail();
        } catch (Exception e) {
        }

        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueTo(400);
            fail();
        } catch (Exception e) {
        }
    }
}
