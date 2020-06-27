package org.gorczyca.pum.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;
import org.gorczyca.pum.project5.CameraAndGalleryActivity;
import org.gorczyca.pum.project5.EnglishLessonMainActivity;

public class MenuProject5Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTaskEnglishLesson;
    private Button buttonTaskCameraAndGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_project_5);
        setTitle(R.string.project_dialogs_and_intents);
        boundIds();
        setOnClickListeners();
    }

    private void boundIds() {
        buttonTaskEnglishLesson = findViewById(R.id.button_task_english_lesson);
        buttonTaskCameraAndGallery = findViewById(R.id.button_task_camera_and_gallery);

    }

    private void setOnClickListeners() {
        buttonTaskEnglishLesson.setOnClickListener(this);
        buttonTaskCameraAndGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonTaskEnglishLesson.getId()) {
            startActivity(new Intent(MenuProject5Activity.this, EnglishLessonMainActivity.class));
        } else if (v.getId() == buttonTaskCameraAndGallery.getId()) {
            startActivity(new Intent(MenuProject5Activity.this, CameraAndGalleryActivity.class));
        }
    }
}
