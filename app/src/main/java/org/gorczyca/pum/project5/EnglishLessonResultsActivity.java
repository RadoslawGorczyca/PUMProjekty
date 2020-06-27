package org.gorczyca.pum.project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.gorczyca.pum.R;

import java.util.Locale;

public class EnglishLessonResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private final String EXTRA_POINTS = "points";
    private final String EXTRA_WORDS_COUNT = "wordsCount";

    private TextView textResults;
    private Button buttonBackToMain;

    private int points;
    private int maxPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_5_english_lesson_results);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            points = bundle.getInt(EXTRA_POINTS);
            maxPoints = bundle.getInt(EXTRA_WORDS_COUNT);
        } else {
            finish();
        }
        bindIds();
        setListeners();
        setUI();
    }

    private void bindIds() {
        textResults = findViewById(R.id.text_results);
        buttonBackToMain = findViewById(R.id.button_back_to_main);
    }

    private void setListeners() {
        buttonBackToMain.setOnClickListener(this);
    }

    private void setUI() {
        textResults.setText(String.format(Locale.getDefault(), "%1$s/%2$s", points, maxPoints));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonBackToMain.getId()){
            finish();
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
