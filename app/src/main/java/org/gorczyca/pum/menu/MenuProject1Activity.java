package org.gorczyca.pum.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.gorczyca.pum.project1.FlashlightActivity;
import org.gorczyca.pum.project1.GuessMyNumberActivity;
import org.gorczyca.pum.project1.QuadraticFunctionsActivity;
import org.gorczyca.pum.R;

public class MenuProject1Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonTaskFlashlight;
    private Button buttonTaskGuessMyNumber;
    private Button buttonTaskQuadraticFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_project_1);
        setTitle(R.string.introduction_to_android);
        boundIds();
        setOnClickListeners();
    }

    private void boundIds(){
        buttonTaskFlashlight = findViewById(R.id.button_task_flashlight);
        buttonTaskGuessMyNumber = findViewById(R.id.button_task_guess_my_number);
        buttonTaskQuadraticFunctions = findViewById(R.id.button_task_quadratic_functions);
    }

    private void setOnClickListeners() {
        buttonTaskFlashlight.setOnClickListener(this);
        buttonTaskGuessMyNumber.setOnClickListener(this);
        buttonTaskQuadraticFunctions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonTaskFlashlight.getId()){
            startActivity(new Intent(MenuProject1Activity.this, FlashlightActivity.class));
        } else if(v.getId() == buttonTaskGuessMyNumber.getId()){
            startActivity(new Intent(MenuProject1Activity.this, GuessMyNumberActivity.class));
        } else if(v.getId() == buttonTaskQuadraticFunctions.getId()){
            startActivity(new Intent(MenuProject1Activity.this, QuadraticFunctionsActivity.class));
        }
    }
}
