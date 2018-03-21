package com.stedi.randomimagegenerator;

import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CountTest {
    @Test
    public void test() {
        Rig.Builder builder = new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(100).setFixedHeight(100);

        assertTrue(builder.build().getCount() == 1);

        builder.setCount(13);
        assertTrue(builder.build().getCount() == 13);

        builder.setWidthRange(1, 10, 1);
        assertTrue(builder.build().getCount() == 10);

        builder.setWidthRange(10, 10, 1);
        assertTrue(builder.build().getCount() == 2);

        builder.setWidthRange(1, 10, 5);
        assertTrue(builder.build().getCount() == 3);

        builder.setFixedWidth(1);

        builder.setHeightRange(10, 1, 1);
        assertTrue(builder.build().getCount() == 10);

        builder.setHeightRange(10, 10, 100);
        assertTrue(builder.build().getCount() == 2);

        builder.setHeightRange(10, 1, 5);
        assertTrue(builder.build().getCount() == 3);

        builder.setWidthRange(1, 10, 5);
        builder.setHeightRange(10, 1, 5);
        assertTrue(builder.build().getCount() == 9);

        builder.setWidthRange(100, 1000, 100);
        builder.setHeightRange(500, 100, 100);
        assertTrue(builder.build().getCount() == 50);
    }
}
