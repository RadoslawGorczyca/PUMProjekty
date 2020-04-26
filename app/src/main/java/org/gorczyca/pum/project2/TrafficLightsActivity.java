package org.gorczyca.pum.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.gorczyca.pum.R;

public class TrafficLightsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageLightRed;
    private ImageView imageLightYellow;
    private ImageView imageLightGreen;
    private Button buttonLightRed;
    private Button buttonLightYellow;
    private Button buttonLightGreen;

    private final int RED_LIGHT = 1;
    private final int YELLOW_LIGHT = 2;
    private final int GREEN_LIGHT = 3;

    private boolean isRedLightOn = false;
    private boolean isYellowLightOn = false;
    private boolean isGreenLightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_2_traffic_lights);
        setTitle(R.string.traffic_lights);
        boundIds();
        setListeners();
        setUI();
    }

    private void boundIds() {
        imageLightRed = findViewById(R.id.image_light_red);
        imageLightYellow = findViewById(R.id.image_light_yellow);
        imageLightGreen = findViewById(R.id.image_light_green);
        buttonLightRed = findViewById(R.id.button_red);
        buttonLightYellow = findViewById(R.id.button_yellow);
        buttonLightGreen = findViewById(R.id.button_green);
    }

    private void setListeners() {
        buttonLightRed.setOnClickListener(this);
        buttonLightYellow.setOnClickListener(this);
        buttonLightGreen.setOnClickListener(this);
    }

    private void setUI() {
        imageLightRed.setImageDrawable(getDrawable(R.drawable.light_off));
        imageLightYellow.setImageDrawable(getDrawable(R.drawable.light_off));
        imageLightGreen.setImageDrawable(getDrawable(R.drawable.light_off));
        buttonLightRed.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        buttonLightYellow.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_orange_dark));
        buttonLightGreen.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
        buttonLightRed.setEnabled(true);
        buttonLightYellow.setEnabled(true);
        buttonLightGreen.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonLightRed.getId()) {
            switchLights(RED_LIGHT);
        } else if (v.getId() == buttonLightYellow.getId()) {
            switchLights(YELLOW_LIGHT);
        } else if (v.getId() == buttonLightGreen.getId()) {
            switchLights(GREEN_LIGHT);
        }
    }

    private void switchLights(int lightColor) {

        imageLightRed.setImageDrawable(getDrawable(R.drawable.light_off));
        imageLightYellow.setImageDrawable(getDrawable(R.drawable.light_off));
        imageLightGreen.setImageDrawable(getDrawable(R.drawable.light_off));
        buttonLightRed.setEnabled(false);
        buttonLightYellow.setEnabled(false);
        buttonLightGreen.setEnabled(false);
        buttonLightRed.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
        buttonLightYellow.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
        buttonLightGreen.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));

        switch (lightColor) {
            case RED_LIGHT:
                imageLightRed.setImageDrawable(getDrawable(R.drawable.red_on));
                buttonLightYellow.setEnabled(true);
                buttonLightYellow.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_orange_dark));
                isRedLightOn = true;
                isYellowLightOn = false;
                isGreenLightOn = false;
                break;
            case YELLOW_LIGHT:
                imageLightYellow.setImageDrawable(getDrawable(R.drawable.yellow_on));
                isYellowLightOn = true;
                isGreenLightOn = false;

                if(isRedLightOn){
                    imageLightRed.setImageDrawable(getDrawable(R.drawable.red_on));
                    buttonLightGreen.setEnabled(true);
                    buttonLightGreen.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
                    isRedLightOn = true;
                } else {
                    buttonLightRed.setEnabled(true);
                    buttonLightRed.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
                    isRedLightOn = false;
                }
                break;
            case GREEN_LIGHT:
                imageLightGreen.setImageDrawable(getDrawable(R.drawable.green_on));
                buttonLightYellow.setEnabled(true);
                buttonLightYellow.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_orange_dark));
                isRedLightOn = false;
                isYellowLightOn = false;
                isGreenLightOn = true;
                break;
        }

    }


}