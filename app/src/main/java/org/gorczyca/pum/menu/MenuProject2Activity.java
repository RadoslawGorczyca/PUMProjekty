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
import org.gorczyca.pum.utils.Constants;

public class MenuProject2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTaskTrafficLights;
    private Button buttonTaskTicTacToe;
    private Button buttonTaskQuiz;
    private Button buttonTaskPizza;
    private Button buttonTaskCountries;
    private Button buttonCountriesXmlArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_project_2);
        setTitle(R.string.project_GUI);
        boundIds();
        setOnClickListeners();
    }

    private void boundIds() {
        buttonTaskTrafficLights = findViewById(R.id.button_task_traffic_lights);
        buttonTaskTicTacToe = findViewById(R.id.button_task_tic_tac_toe);
        buttonTaskQuiz = findViewById(R.id.button_task_quiz);
        buttonTaskPizza = findViewById(R.id.button_task_pizza);
        buttonTaskCountries = findViewById(R.id.button_task_countries);
        buttonCountriesXmlArrays = findViewById(R.id.button_task_countries_xml_arrays);
    }

    private void setOnClickListeners() {
        buttonTaskTrafficLights.setOnClickListener(this);
        buttonTaskTicTacToe.setOnClickListener(this);
        buttonTaskQuiz.setOnClickListener(this);
        buttonTaskPizza.setOnClickListener(this);
        buttonTaskCountries.setOnClickListener(this);
        buttonCountriesXmlArrays.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonTaskTrafficLights.getId()) {
            startActivity(new Intent(MenuProject2Activity.this, TrafficLightsActivity.class));
        } else if (v.getId() == buttonTaskTicTacToe.getId()) {
            startActivity(new Intent(MenuProject2Activity.this, TicTacToeActivity.class));
        } else if (v.getId() == buttonTaskQuiz.getId()) {
            startActivity(new Intent(MenuProject2Activity.this, QuizActivity.class));
        } else if (v.getId() == buttonTaskPizza.getId()) {
            startActivity(new Intent(MenuProject2Activity.this, PizzaActivity.class));
        } else if (v.getId() == buttonTaskCountries.getId()) {
            Intent intent = new Intent(MenuProject2Activity.this, CountriesActivity.class);
            intent.putExtra(Constants.INTENT_KEY_USING_XML_ARRAYS, false);
            startActivity(intent);
        } else if (v.getId() == buttonCountriesXmlArrays.getId()) {
            Intent intent = new Intent(MenuProject2Activity.this, CountriesActivity.class);
            intent.putExtra(Constants.INTENT_KEY_USING_XML_ARRAYS, true);
            startActivity(intent);
        }
    }
}
