package org.gorczyca.pum.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;
import org.gorczyca.pum.project2.CountriesActivity;
import org.gorczyca.pum.project2.PizzaActivity;
import org.gorczyca.pum.project2.QuizActivity;
import org.gorczyca.pum.project2.TicTacToeActivity;
import org.gorczyca.pum.project2.TrafficLightsActivity;
import org.gorczyca.pum.project3.GalleryActivity;
import org.gorczyca.pum.project3.TextToSpeechActivity;
import org.gorczyca.pum.utils.Constants;

public class MenuProject3Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTaskGallery;
    private Button buttonTaskTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_project_3);
        setTitle(R.string.project_files_photos_and_TTS);
        boundIds();
        setOnClickListeners();
    }

    private void boundIds() {
        buttonTaskGallery = findViewById(R.id.button_task_gallery);
        buttonTaskTextToSpeech = findViewById(R.id.button_task_text_to_speech);

    }

    private void setOnClickListeners() {
        buttonTaskGallery.setOnClickListener(this);
        buttonTaskTextToSpeech.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonTaskGallery.getId()) {
            startActivity(new Intent(MenuProject3Activity.this, GalleryActivity.class));
        } else if (v.getId() == buttonTaskTextToSpeech.getId()) {
            startActivity(new Intent(MenuProject3Activity.this, TextToSpeechActivity.class));
        }
    }
}
