package org.gorczyca.pum.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.gorczyca.pum.R;
import org.gorczyca.pum.projectEnd.ToDoMainActivity;

public class MenuMainPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonProjectIntroduction;
    private Button buttonProjectGUI;
    private Button buttonProjectFilesPhotosTTS;
    private Button buttonProjectWebView;
    private Button buttonProjectDialogsIntentions;
    private Button buttonProjectEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main_page);
        boundIds();
        setOnClickListeners();
    }

    private void boundIds() {
        buttonProjectIntroduction = findViewById(R.id.button_project_introduction);
        buttonProjectGUI = findViewById(R.id.button_project_GUI);
        buttonProjectFilesPhotosTTS = findViewById(R.id.button_project_files_photos_and_TTS);
        buttonProjectWebView = findViewById(R.id.button_project_web_view);
        buttonProjectDialogsIntentions = findViewById(R.id.button_project_dialogs_and_intents);
        buttonProjectEnd = findViewById(R.id.button_project_end);
    }

    private void setOnClickListeners() {
        buttonProjectIntroduction.setOnClickListener(this);
        buttonProjectGUI.setOnClickListener(this);
        buttonProjectFilesPhotosTTS.setOnClickListener(this);
        buttonProjectWebView.setOnClickListener(this);
        buttonProjectDialogsIntentions.setOnClickListener(this);
        buttonProjectEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonProjectIntroduction.getId()) {
            startActivity(new Intent(MenuMainPageActivity.this, MenuProject1Activity.class));
        } else if (v.getId() == buttonProjectGUI.getId()) {
            startActivity(new Intent(MenuMainPageActivity.this, MenuProject2Activity.class));
        } else if (v.getId() == buttonProjectFilesPhotosTTS.getId()) {
            //TODO Podepnij OnClick po ukonczeniu projektu 3
        } else if (v.getId() == buttonProjectWebView.getId()) {
            //TODO Podepnij OnClick po ukonczeniu projektu 4
        } else if (v.getId() == buttonProjectDialogsIntentions.getId()) {
            //TODO Podepnij OnClick po ukonczeniu projektu 5
        } else if (v.getId() == buttonProjectEnd.getId()) {
            startActivity(new Intent(MenuMainPageActivity.this, ToDoMainActivity.class));
        }
    }
}
