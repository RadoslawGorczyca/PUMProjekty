package org.gorczyca.pum.project2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.QuizQuestionItem;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textQuestionNumber;
    private TextView textPointsNumber;
    private ImageView imageQuestion;
    private Button buttonAnswer0;
    private Button buttonAnswer1;
    private Button buttonAnswer2;
    private Button buttonAnswer3;

    private ArrayList<QuizQuestionItem> questionItemArrayList = new ArrayList<>();
    private QuizQuestionItem currentQuestionItem;
    private int questionNumber = 0;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_2_quiz);
        setTitle(R.string.quiz_archery);
        bindViewIds();
        bindOnClickListeners();
        createQuizQuestions();
        setUIForNextQuestion();
    }

    private void bindViewIds() {
        textQuestionNumber = findViewById(R.id.text_question_number);
        textPointsNumber = findViewById(R.id.text_points_number);
        imageQuestion = findViewById(R.id.image_question_picture);
        buttonAnswer0 = findViewById(R.id.button_answer0);
        buttonAnswer1 = findViewById(R.id.button_answer1);
        buttonAnswer2 = findViewById(R.id.button_answer2);
        buttonAnswer3 = findViewById(R.id.button_answer3);
    }

    private void bindOnClickListeners() {
        buttonAnswer0.setOnClickListener(this);
        buttonAnswer1.setOnClickListener(this);
        buttonAnswer2.setOnClickListener(this);
        buttonAnswer3.setOnClickListener(this);
    }

    private void createQuizQuestions() {
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.bowstringer), new String[]{"Majdan", "Bowstringer", "Cięciwa", "Ramiona"}, 1));
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.button), new String[]{"Button", "Clicker", "Plastron", "Majdan"}, 0));
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.clicker), new String[]{"Karwasz", "Button", "Clicker", "Grot"}, 2));
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.karwasz), new String[]{"Majdan", "Plastron", "Karwasz", "Ramiona"}, 2));
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.majdan), new String[]{"Majdan", "Bowstringer", "Button", "Cięciwa"}, 0));
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.plastron), new String[]{"Palcat", "Karwasz", "Grot", "Plastron"}, 3));
        questionItemArrayList.add(new QuizQuestionItem(getResources().getDrawable(R.drawable.ramiona), new String[]{"Lotki", "Ramiona", "Inserty", "Kołczany"}, 1));
    }

    private void setUIForNextQuestion() {
        questionNumber++;
        if (questionNumber <= questionItemArrayList.size()) {
            textQuestionNumber.setText(String.format(getString(R.string.question_number), questionNumber, questionItemArrayList.size()));
            textPointsNumber.setText(String.format(getString(R.string.points_number), points));
            resetButtonsColor();

            currentQuestionItem = questionItemArrayList.get(questionNumber - 1);

            imageQuestion.setImageDrawable(currentQuestionItem.getQuestionDrawable());
            buttonAnswer0.setText(currentQuestionItem.getAnswers()[0]);
            buttonAnswer1.setText(currentQuestionItem.getAnswers()[1]);
            buttonAnswer2.setText(currentQuestionItem.getAnswers()[2]);
            buttonAnswer3.setText(currentQuestionItem.getAnswers()[3]);
            setButtonsEnabled(true);
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(Button answerButton) {
        setButtonsEnabled(false);
        answerButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        switch (currentQuestionItem.getCorrectAnswerId()) {
            case 0:
                buttonAnswer0.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
                if (answerButton.getId() == buttonAnswer0.getId()) {
                    points++;
                }
                break;
            case 1:
                buttonAnswer1.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
                if (answerButton.getId() == buttonAnswer1.getId()) {
                    points++;
                }
                break;
            case 2:
                buttonAnswer2.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
                if (answerButton.getId() == buttonAnswer2.getId()) {
                    points++;
                }
                break;
            case 3:
                buttonAnswer3.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
                if (answerButton.getId() == buttonAnswer3.getId()) {
                    points++;
                }
                break;
        }

        textPointsNumber.setText(String.format(getString(R.string.points_number), points));
        (new Handler()).postDelayed(this::setUIForNextQuestion, 3000);

    }

    private void setButtonsEnabled(boolean isEnabled) {
        buttonAnswer0.setEnabled(isEnabled);
        buttonAnswer1.setEnabled(isEnabled);
        buttonAnswer2.setEnabled(isEnabled);
        buttonAnswer3.setEnabled(isEnabled);
    }

    private void resetButtonsColor() {
        buttonAnswer0.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
        buttonAnswer1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
        buttonAnswer2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
        buttonAnswer3.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
    }

    private void endQuiz() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(textPointsNumber.getText())
                .setMessage(R.string.quiz_end_text)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                .show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonAnswer0.getId() ||
                v.getId() == buttonAnswer1.getId() ||
                v.getId() == buttonAnswer2.getId() ||
                v.getId() == buttonAnswer3.getId()) {
            checkAnswer((Button) v);
        }
    }
}
