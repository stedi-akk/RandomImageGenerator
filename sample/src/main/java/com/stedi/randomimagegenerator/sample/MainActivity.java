package com.stedi.randomimagegenerator.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Quality;
import com.stedi.randomimagegenerator.Rig;
import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.callbacks.SaveCallback;
import com.stedi.randomimagegenerator.generators.ColoredCirclesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredLinesGenerator;
import com.stedi.randomimagegenerator.generators.ColoredNoiseGenerator;
import com.stedi.randomimagegenerator.generators.ColoredPixelsGenerator;
import com.stedi.randomimagegenerator.generators.ColoredRectangleGenerator;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.Generator;
import com.stedi.randomimagegenerator.generators.GeneratorPack;
import com.stedi.randomimagegenerator.generators.effects.MirroredEffect;
import com.stedi.randomimagegenerator.generators.effects.TextOverlayEffect;
import com.stedi.randomimagegenerator.generators.effects.ThresholdEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        View.OnClickListener,
        GenerateCallback,
        SaveCallback {

    private Spinner spGenerator;
    private Spinner spQualityFormat;
    private CheckBox cbTextOverlay;
    private CheckBox cbMirrored;
    private CheckBox cbThreshold;
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

        spGenerator = findViewById(R.id.main_activity_sp_generator);
        spGenerator.setOnItemSelectedListener(this);
        spGenerator.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{
                "GeneratorPack",
                "FlatColorGenerator",
                "ColoredPixelsGenerator",
                "ColoredNoiseGenerator",
                "ColoredCirclesGenerator",
                "ColoredRectangleGenerator",
                "ColoredLinesGenerator"
        }));

        spQualityFormat = findViewById(R.id.main_activity_sp_quality_format);
        spQualityFormat.setOnItemSelectedListener(this);
        spQualityFormat.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{
                Bitmap.CompressFormat.PNG.name(),
                Bitmap.CompressFormat.JPEG.name(),
                Bitmap.CompressFormat.WEBP.name()
        }));

        cbTextOverlay = findViewById(R.id.main_activity_cb_text_overlay);
        cbMirrored = findViewById(R.id.main_activity_cb_mirrored);
        cbThreshold = findViewById(R.id.main_activity_cb_threshold);
        cbSaveFile = findViewById(R.id.main_activity_cb_save_file);

        findViewById(R.id.main_activity_btn_generate_1).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_6).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_30).setOnClickListener(this);
        findViewById(R.id.main_activity_btn_generate_range).setOnClickListener(this);

        recyclerView = findViewById(R.id.main_activity_rv);
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
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Generator generator = selectedGenerator;

                if (cbThreshold.isChecked()) {
                    generator = new ThresholdEffect(generator);
                }

                if (cbMirrored.isChecked()) {
                    generator = new MirroredEffect(generator);
                }

                if (cbTextOverlay.isChecked()) {
                    generator = new TextOverlayEffect.Builder()
                            .setGenerator(generator).build();
                }

                Rig.Builder builder = new Rig.Builder()
                        .setCallback(MainActivity.this)
                        .setGenerator(generator)
                        .setQuality(new Quality(selectedQualityFormat, 100));

                if (count > 0) {
                    builder.setFixedSize(600, 600);
                    builder.setCount(count);
                } else {
                    builder.setHeightRange(400, 1000, 200);
                    builder.setWidthRange(800, 500, 100);
                }

                if (cbSaveFile.isChecked()) {
                    String picturesFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
                    builder.setFileSavePath(picturesFolder + File.separator + "rig");
                    builder.setFileSaveCallback(MainActivity.this);
                }

                builder.build().generate();
            }
        });
    }

    @Override
    public void onGenerated(@NonNull ImageParams imageParams, @NonNull final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imagesAdapter.add(bitmap);
                recyclerView.scrollToPosition(imagesAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onFailedToGenerate(@NonNull final ImageParams imageParams, @NonNull final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failed to generate id " + imageParams.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaved(@NonNull Bitmap bitmap, @NonNull File file) {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    @Override
    public void onFailedToSave(@NonNull Bitmap bitmap, @NonNull final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "failed to save image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Generator generatorFactory(String className) {
        switch (className) {
            case "GeneratorPack":
                return new GeneratorPack(new ArrayList<>(Arrays.asList(new Generator[]{
                        generatorFactory("ColoredPixelsGenerator"),
                        generatorFactory("ColoredNoiseGenerator"),
                        generatorFactory("ColoredCirclesGenerator"),
                        generatorFactory("ColoredRectangleGenerator"),
                        generatorFactory("ColoredLinesGenerator"),
                })));
            case "FlatColorGenerator":
                return new FlatColorGenerator();
            case "ColoredPixelsGenerator":
                return new ColoredPixelsGenerator(20);
            case "ColoredNoiseGenerator":
                return new ColoredNoiseGenerator();
            case "ColoredCirclesGenerator":
                return new ColoredCirclesGenerator();
            case "ColoredRectangleGenerator":
                return new ColoredRectangleGenerator();
            case "ColoredLinesGenerator":
                return new ColoredLinesGenerator();
            default:
                throw new IllegalStateException();
        }
    }

    private Bitmap.CompressFormat qualityFormatFactory(String enumName) {
        for (Bitmap.CompressFormat cf : Bitmap.CompressFormat.values()) {
            if (cf.name().equals(enumName)) {
                return cf;
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
