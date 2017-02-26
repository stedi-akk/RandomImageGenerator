package com.stedi.randomimagegenerator.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;
import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;

public class MainActivity extends AppCompatActivity implements GenerateCallback {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        imageView = (ImageView) findViewById(R.id.main_activity_iv);

        findViewById(R.id.main_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Rig.Builder()
                                .setGenerator(new FlatColorGenerator())
                                .setCallback(MainActivity.this)
                                .setFixedSize(500, 500)
                                .build().generate();
                    }
                }).start();
            }
        });
    }

    @Override
    public void onGenerated(ImageParams imageParams, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MainActivity.this, "bitmap == null", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onException(ImageParams imageParams, final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failed to generate", Toast.LENGTH_LONG).show();
            }
        });
    }
}
