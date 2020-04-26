package org.gorczyca.pum.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.gorczyca.pum.R;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuessMyNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputNumber;
    private ImageButton buttonGuess;
    private TextView textHint;
    private TextView textEnd;
    private TextView textPlayAgain;
    private Button buttonPlayAgain;
    private TextView textNumberOfTries;
    private TextView textNumberOfPoints;

    private int numberOfTries = 0;
    private int numberOfPoints = 0;
    private int correctNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_1_guess_my_number);
        setTitle(R.string.guess_my_number);
        boundIds();
        setListeners();
        setNewGame();
    }

    private void boundIds() {
        inputNumber = findViewById(R.id.input_number);
        buttonGuess = findViewById(R.id.button_guess);
        textHint = findViewById(R.id.text_hint);
        textEnd = findViewById(R.id.text_end);
        textPlayAgain = findViewById(R.id.text_play_again);
        buttonPlayAgain = findViewById(R.id.button_play_again);
        textNumberOfTries = findViewById(R.id.text_number_of_tries);
        textNumberOfPoints = findViewById(R.id.text_number_of_points);
    }

    private void setListeners() {
        buttonGuess.setOnClickListener(this);
        buttonPlayAgain.setOnClickListener(this);
        inputNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    onClick(buttonGuess);
                    return true;
                }
                return false;
            }
        });
    }

    private void setNewGame() {
        inputNumber.setVisibility(View.VISIBLE);
        buttonGuess.setVisibility(View.VISIBLE);
        inputNumber.setText("");
        textHint.setText("");
        textHint.setVisibility(View.GONE);
        textEnd.setText("");
        textEnd.setVisibility(View.GONE);
        textPlayAgain.setVisibility(View.GONE);
        buttonPlayAgain.setVisibility(View.GONE);
        numberOfTries = 0;
        textNumberOfTries.setText(String.format(getString(R.string.number_of_tries), numberOfTries));
        textNumberOfPoints.setText(String.format(getString(R.string.number_of_points), numberOfPoints));
        correctNumber = new Random().nextInt(4001) - 2000;
        Log.d("GUESS_NUMBER", "Prawidłowa liczba: " + correctNumber);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonGuess.getId()) {
            if (checkIfInputCorrect(inputNumber)) {
                int guessedNumber = getInputNumber(inputNumber);
                checkGuessedNumber(guessedNumber, correctNumber);
                incrementNumberOfTries();
            } else {
                inputNumber.setError(getString(R.string.error_insert_correct_number));
            }
        } else if (v.getId() == buttonPlayAgain.getId()) {
            setNewGame();
        }
    }

    private boolean checkIfInputCorrect(EditText inputNumber) {
        String input = inputNumber.getText().toString();
        if (input.isEmpty()) {
            return false;
        }
        String regex = "[+-]?[0-9][0-9]*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.find() && m.group().equals(input);
    }

    private int getInputNumber(EditText inputNumber) {
        int number = Integer.parseInt(inputNumber.getText().toString());
        inputNumber.setText("");
        return number;
    }

    private void checkGuessedNumber(int guessedNumber, int correctNumber) {
        textHint.setVisibility(View.VISIBLE);
        if (correctNumber > guessedNumber) {
            textHint.setText(R.string.bigger);
        } else if (correctNumber < guessedNumber) {
            textHint.setText(R.string.smaller);
        } else {
            endGame();
        }
    }

    private void endGame() {
        incrementNumberOfPoints();
        textHint.setVisibility(View.GONE);
        textEnd.setText(String.format(getString(R.string.congratulations_the_correct_number_was), correctNumber));
        textEnd.setVisibility(View.VISIBLE);
        textPlayAgain.setVisibility(View.VISIBLE);
        buttonPlayAgain.setVisibility(View.VISIBLE);
        inputNumber.setVisibility(View.GONE);
        buttonGuess.setVisibility(View.GONE);
    }

    private void incrementNumberOfTries() {
        numberOfTries++;
        textNumberOfTries.setText(String.format(getString(R.string.number_of_tries), numberOfTries));
    }

    private void incrementNumberOfPoints() {
        numberOfPoints++;
        textNumberOfPoints.setText(String.format(getString(R.string.number_of_points), numberOfPoints));
    }
}
