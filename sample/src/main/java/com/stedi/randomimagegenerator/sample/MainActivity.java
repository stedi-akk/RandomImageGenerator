package com.stedi.randomimagegenerator.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Quality;
import com.stedi.randomimagegenerator.Rig;
import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.generators.ColoredCirclesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredNoiseGenerator;
import com.stedi.randomimagegenerator.generators.ColoredPixelsGenerator;
import com.stedi.randomimagegenerator.generators.ColoredRectangleGenerator;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.Generator;
import com.stedi.randomimagegenerator.generators.MirroredGenerator;
import com.stedi.randomimagegenerator.generators.TextOverlayGenerator;

import java.io.File;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        View.OnClickListener,
        GenerateCallback,
        SaveCallback {

    private Spinner spGenerator;
    private Spinner spQualityFormat;
    private CheckBox cbTextOverlay;
    private CheckBox cbMirrored;
    private CheckBox cbSaveFile;
    private RecyclerView recyclerView;
    private ImagesAdapter imagesAdapter;

    private Generator selectedGenerator = new FlatColorGenerator();
    private Bitmap.CompressFormat selectedQualityFormat = Bitmap.CompressFormat.PNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Rig.enableDebugLogging(true);
        setContentView(R.layout.main_activity);

        spGenerator = (Spinner) findViewById(R.id.main_activity_sp_generator);
        spGenerator.setOnItemSelectedListener(this);
        spGenerator.setAdapter(createSpinnerAdapter(new String[]{
                FlatColorGenerator.class.getSimpleName(),
                ColoredPixelsGenerator.class.getSimpleName(),
                ColoredNoiseGenerator.class.getSimpleName(),
                ColoredCirclesGenerator.class.getSimpleName(),
                ColoredRectangleGenerator.class.getSimpleName()
        }));

        spQualityFormat = (Spinner) findViewById(R.id.main_activity_sp_quality_format);
        spQualityFormat.setOnItemSelectedListener(this);
        spQualityFormat.setAdapter(createSpinnerAdapter(new String[]{
                Bitmap.CompressFormat.PNG.name(),
                Bitmap.CompressFormat.JPEG.name(),
                Bitmap.CompressFormat.WEBP.name()
        }));

        cbTextOverlay = (CheckBox) findViewById(R.id.main_activity_cb_text_overlay);
        cbMirrored = (CheckBox) findViewById(R.id.main_activity_cb_mirrored);
        cbSaveFile = (CheckBox) findViewById(R.id.main_activity_cb_save_file);

        findViewById(R.id.main_activity_btn_generate_1).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_6).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_30).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_range).setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.main_activity_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imagesAdapter = new ImagesAdapter();
        recyclerView.setAdapter(imagesAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) parent.getItemAtPosition(position);
        if (parent == spGenerator) {
            selectedGenerator = generatorFactory(item);
        } else if (parent == spQualityFormat) {
            selectedQualityFormat = qualityFormatFactory(item);
        }
    }

    @Override
    public void onClick(View v) {
        imagesAdapter.clear();
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
                generate(-1);
                break;
        }
    }

    private void generate(final int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Generator generator = selectedGenerator;
                if (cbMirrored.isChecked())
                    generator = new MirroredGenerator(generator);
                if (cbTextOverlay.isChecked())
                    generator = new TextOverlayGenerator.Builder()
                            .setGenerator(generator).build();

                Rig.Builder builder = new Rig.Builder()
                        .setCallback(MainActivity.this)
                        .setGenerator(generator)
                        .setQuality(new Quality(selectedQualityFormat, 100));

                if (count > 0) {
                    builder.setFixedSize(600, 600);
                    builder.setCount(count);
                } else {
                    builder.setHeightRange(200, 800, 200);
                    builder.setWidthRange(600, 300, 100);
                }

                if (cbSaveFile.isChecked()) {
                    String picturesFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
                    builder.setFileSavePath(picturesFolder + File.separator + "rig");
                    builder.setFileSaveCallback(MainActivity.this);
                }

                builder.build().generate();
            }
        }).start();
    }

    @Override
    public void onGenerated(ImageParams imageParams, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imagesAdapter.add(bitmap);
                recyclerView.scrollToPosition(imagesAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onFailedToGenerate(final ImageParams imageParams, final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failed to generate id " + imageParams.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaved(Bitmap bitmap, File file) {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    @Override
    public void onFailedToSave(Bitmap bitmap, final File file, final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failed to save file " + file.getPath(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private SpinnerAdapter createSpinnerAdapter(String[] items) {
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    }

    private Generator generatorFactory(String className) {
        if (className.equals(ColoredPixelsGenerator.class.getSimpleName()))
            return new ColoredPixelsGenerator(20);
        if (className.equals(ColoredNoiseGenerator.class.getSimpleName()))
            return new ColoredNoiseGenerator();
        if (className.equals(ColoredCirclesGenerator.class.getSimpleName()))
            return new ColoredCirclesGenerator();
        if (className.equals(ColoredRectangleGenerator.class.getSimpleName()))
            return new ColoredRectangleGenerator();
        return new FlatColorGenerator();
    }

    private Bitmap.CompressFormat qualityFormatFactory(String enumName) {
        for (Bitmap.CompressFormat cf : Bitmap.CompressFormat.values())
            if (cf.name().equals(enumName))
                return cf;
        return Bitmap.CompressFormat.PNG;
    }
}
