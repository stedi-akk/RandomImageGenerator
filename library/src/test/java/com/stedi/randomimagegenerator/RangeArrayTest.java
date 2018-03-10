package com.stedi.randomimagegenerator;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RangeArrayTest {
    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgs() {
        Rig.createRangeArray(0, 0, 0);
    }

    @Test
    public void testEqualsFromTo() {
        for (int step = 1; step <= 10; step++) {
            for (int val = 100; val <= 1000; val += 100) {
                int[] result = Rig.createRangeArray(val, val, step);
                assertTrue(result.length == 2);
                assertTrue(result[0] == val);
                assertTrue(result[1] == val);
            }
        }
    }

    @Test
    public void test1() {
        for (int from = 1; from <= 10; from += 1) {
            for (int to = 10; to <= 20; to += 1) {
                int[] result = Rig.createRangeArray(from, to, 1);
                int size = to == from ? 2 : to - from + 1;
                assertTrue(result.length + " != " + size, result.length == size);
                assertTrue(result[0] == from);
                for (int i = 1, val = from + 1; i < result.length - 1; i++, val++) {
                    assertTrue(result[i] + " != " + val, result[i] == val);
                }
                assertTrue(result[result.length - 1] == to);
            }
        }
    }

    @Test
    public void test1Negative() {
        for (int from = 20; from >= 10; from -= 1) {
            for (int to = 10; to >= 1; to -= 1) {
                int[] result = Rig.createRangeArray(from, to, 1);
                int size = to == from ? 2 : Math.abs(to - from - 1);
                assertTrue(result.length + " != " + size, result.length == size);
                assertTrue(result[0] == from);
                for (int i = 1, val = from - 1; i < result.length - 1; i++, val--) {
                    assertTrue(result[i] + " != " + val, result[i] == val);
                }
                assertTrue(result[result.length - 1] == to);
            }
        }
    }

    @Test
    public void test2() {
        for (int step = 2; step <= 9; step++) {
            for (int from = 1; from <= 11; from += 1) {
                for (int to = 11; to <= 19; to += 1) {
                    int[] result = Rig.createRangeArray(from, to, step);
                    assertTrue(result[0] == from);
                    for (int i = 1, val = from + step; i < result.length - 1; i++, val += step) {
                        assertTrue(result[i] + " != " + val, result[i] == val);
                    }
                    assertTrue(result[result.length - 1] == to);
                }
            }
        }
    }

    @Test
    public void test2Negative() {
        for (int step = 2; step <= 9; step++) {
            for (int from = 19; from >= 11; from -= 1) {
                for (int to = 11; to >= 1; to -= 1) {
                    int[] result = Rig.createRangeArray(from, to, step);
                    assertTrue(result[0] == from);
                    for (int i = 1, val = from - step; i < result.length - 1; i++, val -= step) {
                        assertTrue(result[i] + " != " + val, result[i] == val);
                    }
                    assertTrue(result[result.length - 1] == to);
                }
            }
        }
    }

    @Test
    public void test3() {
        for (int step = 211; step <= 944; step += 194) {
            for (int from = 171; from <= 1133; from += step) {
                for (int to = 1166; to <= 1958; to += step) {
                    int[] result = Rig.createRangeArray(from, to, step);
                    assertTrue(result[0] == from);
                    for (int i = 1, val = from + step; i < result.length - 1; i++, val += step) {
                        assertTrue(result[i] + " != " + val, result[i] == val);
                    }
                    assertTrue(result[result.length - 1] == to);
                }
            }
        }
    }

    @Test
    public void test3Negative() {
        for (int step = 211; step >= 94; step -= 12) {
            for (int from = 1710; from >= 513; from -= step) {
                for (int to = 500; to >= 10; to -= step) {
                    int[] result = Rig.createRangeArray(from, to, step);
                    assertTrue(result[0] == from);
                    for (int i = 1, val = from - step; i < result.length - 1; i++, val -= step) {
                        assertTrue(result[i] + " != " + val, result[i] == val);
                    }
                    assertTrue(result[result.length - 1] == to);
                }
            }
        }
    }
}
