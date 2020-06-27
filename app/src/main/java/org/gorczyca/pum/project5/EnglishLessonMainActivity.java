package org.gorczyca.pum.project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import org.gorczyca.pum.R;

public class EnglishLessonMainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int LANGUAGE_ENGLISH = 1;
    private final int LANGUAGE_POLISH = 2;
    private final int MODE_LEARNING = 1;
    private final int MODE_TEST = 2;
    private final String EXTRA_LANGUAGE = "language";
    private final String EXTRA_MODE = "mode";

    private RadioGroup radioGroupLanguage;
    private RadioGroup radioGroupMode;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_5_english_lesson_main);
        setTitle(R.string.english_lesson);
        bindIds();
        bindListeners();
    }

    private void bindIds() {
        radioGroupLanguage = findViewById(R.id.radio_group_language);
        radioGroupMode = findViewById(R.id.radio_group_mode);
        buttonStart = findViewById(R.id.button_start);
    }

    private void bindListeners() {
        buttonStart.setOnClickListener(this);
    }

    private void startTest() {
        Intent intent = new Intent(this, EnglishLessonTestActivity.class);
        int language;
        int mode;
        if(radioGroupLanguage.getCheckedRadioButtonId() == R.id.radio_english){
            language = LANGUAGE_ENGLISH;
        } else {
            language = LANGUAGE_POLISH;
        }
        if(radioGroupMode.getCheckedRadioButtonId() == R.id.radio_learning){
            mode = MODE_LEARNING;
        } else {
            mode = MODE_TEST;
        }
        intent.putExtra(EXTRA_LANGUAGE, language);
        intent.putExtra(EXTRA_MODE, mode);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonStart.getId()){
            startTest();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_5_english_lesson_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about)
                    .setMessage(R.string.about_message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
