package org.gorczyca.pum.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;
import org.gorczyca.pum.project3.GalleryActivity;
import org.gorczyca.pum.project3.TextToSpeechActivity;
import org.gorczyca.pum.project4.ContactBookActivity;
import org.gorczyca.pum.project4.FindPairsActivity;
import org.gorczyca.pum.project4.InterestingPlacesActivity;

public class MenuProject4Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTaskFindPairs;
    private Button buttonTaskInterestingPlaces;
    private Button buttonTaskContactBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_project_4);
        setTitle(R.string.project_web_view);
        boundIds();
        setOnClickListeners();
    }

    private void boundIds() {
        buttonTaskFindPairs = findViewById(R.id.button_task_find_pairs);
        buttonTaskInterestingPlaces = findViewById(R.id.button_task_interesting_places);
        buttonTaskContactBook = findViewById(R.id.button_task_contact_book);

    }

    private void setOnClickListeners() {
        buttonTaskFindPairs.setOnClickListener(this);
        buttonTaskInterestingPlaces.setOnClickListener(this);
        buttonTaskContactBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonTaskFindPairs.getId()) {
            startActivity(new Intent(MenuProject4Activity.this, FindPairsActivity.class));
        } else if (v.getId() == buttonTaskInterestingPlaces.getId()) {
            startActivity(new Intent(MenuProject4Activity.this, InterestingPlacesActivity.class));
        } else if(v.getId() == buttonTaskContactBook.getId()) {
            startActivity(new Intent(MenuProject4Activity.this, ContactBookActivity.class));
        }
    }
}
