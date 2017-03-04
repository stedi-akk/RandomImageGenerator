package com.stedi.randomimagegenerator.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;
import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.TextOverlayGenerator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GenerateCallback {
    private ImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.main_activity_btn_generate_1).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_6).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_30).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_range).setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_activity_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ImagesAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        adapter.clear();
        switch (v.getId()) {
            case R.id.main_activity_btn_generate_1:
                generate(1);
                break;
            case R.id.main_activity_btn_generate_6:
                generate(6);
                break;
            case R.id.main_activity_btn_generate_30:
                generate(30);
                break;
            case R.id.main_activity_btn_generate_range:
                generateRange();
                break;
        }
    }

    private void generate(final int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Rig.Builder()
                        .setCallback(MainActivity.this)
                        .setGenerator(new TextOverlayGenerator(new FlatColorGenerator()))
                        .setFixedSize(600, 600)
                        .setCount(count)
                        .build().generate();
            }
        }).start();
    }

    private void generateRange() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Rig.Builder()
                        .setCallback(MainActivity.this)
                        .setGenerator(new TextOverlayGenerator(new FlatColorGenerator()))
                        .setHeightRange(200, 800, 200)
                        .setWidthRange(600, 300, 100)
                        .build().generate();
            }
        }).start();
    }

    @Override
    public void onGenerated(ImageParams imageParams, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.add(bitmap);
            }
        });
    }

    @Override
    public void onException(ImageParams imageParams, final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failed to generate", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
