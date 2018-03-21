package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.generators.Generator;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class CancelGenerateTest {
    private static final int requestCount = 1000;

    private volatile int generatedCount = 0;
    private volatile int failedCount = 0;

    @Test
    public void test() {
        final Object lock = new Object();

        final Rig rig = new Rig.Builder()
                .setGenerator(new Generator() {
                    @Nullable
                    @Override
                    public Bitmap generate(@NonNull ImageParams imageParams) throws Exception {
                        sleep(100);
                        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                    }
                })
                .setFixedSize(100, 100)
                .setCount(requestCount)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(@NonNull ImageParams imageParams, @NonNull Bitmap bitmap) {
                        generatedCount++;
                    }

                    @Override
                    public void onFailedToGenerate(@NonNull ImageParams imageParams, @NonNull Exception e) {
                        failedCount++;
                    }
                }).build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(1000);
                rig.generate();
                synchronized (lock) {
                    lock.notify();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(2000);
                rig.cancel();
            }
        }).start();

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertTrue(generatedCount > 0 && generatedCount < requestCount);
        assertTrue(failedCount == 0);
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
