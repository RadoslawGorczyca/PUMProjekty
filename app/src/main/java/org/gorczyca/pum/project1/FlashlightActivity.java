package org.gorczyca.pum.project1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.gorczyca.pum.R;

public class FlashlightActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonBack;
    private FloatingActionButton buttonSettings;
    private ConstraintLayout layoutFlashlightSettings;
    private ImageButton buttonCloseSettings;
    private ConstraintLayout layoutFlashlight;
    private Button buttonColorWhite;
    private Button buttonColorRed;
    private Button buttonColorGreen;
    private Button buttonColorBlue;
    private SeekBar alphaSeekBar;
    private int alpha = 255;
    private color flashlightColor = color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_1_flashlight);
        boundIds();
        setListeners();
        setUI();
    }

    private void boundIds() {
        buttonBack = findViewById(R.id.button_back);
        buttonSettings = findViewById(R.id.fab_settings);
        layoutFlashlightSettings = findViewById(R.id.layout_flashlight_settings);
        buttonCloseSettings = findViewById(R.id.button_close_settings);
        layoutFlashlight = findViewById(R.id.layout_flashlight);
        buttonColorWhite = findViewById(R.id.button_color_white);
        buttonColorRed = findViewById(R.id.button_color_red);
        buttonColorGreen = findViewById(R.id.button_color_green);
        buttonColorBlue = findViewById(R.id.button_color_blue);
        alphaSeekBar = findViewById(R.id.seekbar_color_transparency);

    }

    private void setListeners() {
        buttonBack.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
        buttonCloseSettings.setOnClickListener(this);
        buttonColorWhite.setOnClickListener(this);
        buttonColorRed.setOnClickListener(this);
        buttonColorGreen.setOnClickListener(this);
        buttonColorBlue.setOnClickListener(this);
    }

    private void setUI() {
        buttonSettings.setVisibility(View.VISIBLE);
        layoutFlashlightSettings.setVisibility(View.GONE);
        setColorBackground(flashlightColor);
        alphaSeekBar.setMax(255);
        alphaSeekBar.setProgress(alpha);
        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                alpha = seekBar.getProgress();
                setColorBackground(flashlightColor);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonBack.getId()) {
            finish();
        } else if (v.getId() == buttonSettings.getId()) {
            openFlashlightSettings();
        } else if (v.getId() == buttonCloseSettings.getId()) {
            closeFlashlightSettings();
        } else if (v.getId() == buttonColorWhite.getId()) {
            flashlightColor = color.WHITE;
            setColorBackground(flashlightColor);
        } else if (v.getId() == buttonColorRed.getId()) {
            flashlightColor = color.RED;
            setColorBackground(flashlightColor);
        } else if (v.getId() == buttonColorGreen.getId()) {
            flashlightColor = color.GREEN;
            setColorBackground(flashlightColor);
        } else if (v.getId() == buttonColorBlue.getId()) {
            flashlightColor = color.BLUE;
            setColorBackground(flashlightColor);
        }
    }

    private void openFlashlightSettings() {
        buttonSettings.setVisibility(View.GONE);
        layoutFlashlightSettings.setVisibility(View.VISIBLE);
    }

    private void closeFlashlightSettings() {
        buttonSettings.setVisibility(View.VISIBLE);
        layoutFlashlightSettings.setVisibility(View.GONE);
    }

    private void setColorBackground(color color) {
        if (color == FlashlightActivity.color.WHITE) {
            layoutFlashlight.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
        } else if (color == FlashlightActivity.color.RED) {
            layoutFlashlight.setBackgroundColor(Color.argb(alpha, 255, 0, 0));
        } else if (color == FlashlightActivity.color.GREEN) {
            layoutFlashlight.setBackgroundColor(Color.argb(alpha, 0, 255, 0));
        } else if (color == FlashlightActivity.color.BLUE) {
            layoutFlashlight.setBackgroundColor(Color.argb(alpha, 0, 0, 255));
        }
    }

    enum color {
        WHITE, RED, GREEN, BLUE
    }
}
