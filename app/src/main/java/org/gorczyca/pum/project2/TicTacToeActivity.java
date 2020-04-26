package org.gorczyca.pum.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.gorczyca.pum.R;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {

    private enum player {
        CIRCLE, CROSS
    }

    private ImageView imagePlayerCircle;
    private TextView textPlayerCirclePoints;
    private ImageView imagePlayerCross;
    private TextView textPlayerCrossPoints;
    private TextView textGameEnded;
    private Button buttonPlayAgain;
    private ImageView imageField11;
    private ImageView imageField12;
    private ImageView imageField13;
    private ImageView imageField21;
    private ImageView imageField22;
    private ImageView imageField23;
    private ImageView imageField31;
    private ImageView imageField32;
    private ImageView imageField33;

    private int steps = 0;
    private boolean isTie = false;

    private player turn = player.CROSS;
    private int circlePoints = 0;
    private int crossPoints = 0;

    private boolean[][] circlesCrossed = new boolean[][]{
            {false, false, false},
            {false, false, false},
            {false, false, false}};
    private boolean[][] crossesCrossed = new boolean[][]{
            {false, false, false},
            {false, false, false},
            {false, false, false}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_2_tic_tac_toe);
        setTitle(R.string.tic_tac_toe);
        boundIds();
        setListeners();
        setUIForNewGame();
    }

    private void boundIds() {
        imagePlayerCircle = findViewById(R.id.image_player_circle);
        textPlayerCirclePoints = findViewById(R.id.text_points_player_circle);
        imagePlayerCross = findViewById(R.id.image_player_cross);
        textPlayerCrossPoints = findViewById(R.id.text_points_player_cross);
        textGameEnded = findViewById(R.id.text_game_ended);
        buttonPlayAgain = findViewById(R.id.button_play_again);
        imageField11 = findViewById(R.id.image_field_0_0);
        imageField12 = findViewById(R.id.image_field_0_1);
        imageField13 = findViewById(R.id.image_field_0_2);
        imageField21 = findViewById(R.id.image_field_1_0);
        imageField22 = findViewById(R.id.image_field_1_1);
        imageField23 = findViewById(R.id.image_field_1_2);
        imageField31 = findViewById(R.id.image_field_2_0);
        imageField32 = findViewById(R.id.image_field_2_1);
        imageField33 = findViewById(R.id.image_field_2_2);

    }

    private void setListeners() {
        buttonPlayAgain.setOnClickListener(this);
        imageField11.setOnClickListener(this);
        imageField12.setOnClickListener(this);
        imageField13.setOnClickListener(this);
        imageField21.setOnClickListener(this);
        imageField22.setOnClickListener(this);
        imageField23.setOnClickListener(this);
        imageField31.setOnClickListener(this);
        imageField32.setOnClickListener(this);
        imageField33.setOnClickListener(this);
    }

    private void setUIForNewGame() {
        circlesCrossed = new boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}};
        crossesCrossed = new boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}};
        steps = 0;
        isTie = false;

        textPlayerCrossPoints.setText(String.valueOf(crossPoints));
        textPlayerCirclePoints.setText(String.valueOf(circlePoints));

        textGameEnded.setVisibility(View.GONE);
        buttonPlayAgain.setVisibility(View.GONE);

        imageField11.setImageDrawable(null);
        imageField12.setImageDrawable(null);
        imageField13.setImageDrawable(null);
        imageField21.setImageDrawable(null);
        imageField22.setImageDrawable(null);
        imageField23.setImageDrawable(null);
        imageField31.setImageDrawable(null);
        imageField32.setImageDrawable(null);
        imageField33.setImageDrawable(null);

        imageField11.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField12.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField13.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField21.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField22.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField23.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField31.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField32.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        imageField33.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));

        imageField11.setEnabled(true);
        imageField12.setEnabled(true);
        imageField13.setEnabled(true);
        imageField21.setEnabled(true);
        imageField22.setEnabled(true);
        imageField23.setEnabled(true);
        imageField31.setEnabled(true);
        imageField32.setEnabled(true);
        imageField33.setEnabled(true);

        changePlayer(turn);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == imageField11.getId()) {
            crossField(turn, (ImageView) v, 0, 0);
        } else if (v.getId() == imageField12.getId()) {
            crossField(turn, (ImageView) v, 0, 1);
        } else if (v.getId() == imageField13.getId()) {
            crossField(turn, (ImageView) v, 0, 2);
        } else if (v.getId() == imageField21.getId()) {
            crossField(turn, (ImageView) v, 1, 0);
        } else if (v.getId() == imageField22.getId()) {
            crossField(turn, (ImageView) v, 1, 1);
        } else if (v.getId() == imageField23.getId()) {
            crossField(turn, (ImageView) v, 1, 2);
        } else if (v.getId() == imageField31.getId()) {
            crossField(turn, (ImageView) v, 2, 0);
        } else if (v.getId() == imageField32.getId()) {
            crossField(turn, (ImageView) v, 2, 1);
        } else if (v.getId() == imageField33.getId()) {
            crossField(turn, (ImageView) v, 2, 2);
        } else if (v.getId() == buttonPlayAgain.getId()) {
            setUIForNewGame();
        }

    }

    private void crossField(player turn, ImageView field, int row, int column) {
        switch (turn) {
            case CIRCLE:
                field.setImageDrawable(getDrawable(R.drawable.outline_panorama_fish_eye_24));
                field.setEnabled(false);
                circlesCrossed[row][column] = true;
                break;
            case CROSS:
                field.setImageDrawable(getDrawable(R.drawable.baseline_close_24));
                field.setEnabled(false);
                crossesCrossed[row][column] = true;
                break;
        }

        if (checkIfGameEnds(turn)) {
            endGame();
        } else {
            changePlayer(turn);
        }
    }

    private void changePlayer(player lastPlayer) {
        switch (lastPlayer) {
            case CROSS:
                turn = player.CIRCLE;
                imagePlayerCircle.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
                imagePlayerCross.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
                break;
            case CIRCLE:
                turn = player.CROSS;
                imagePlayerCircle.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.black));
                imagePlayerCross.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
                break;
        }
    }

    private boolean checkIfGameEnds(player turn) {
        steps++;
        boolean[][] fieldsToCheck = null;
        switch (turn) {
            case CROSS:
                fieldsToCheck = crossesCrossed;
                break;
            case CIRCLE:
                fieldsToCheck = circlesCrossed;
                break;
        }
        boolean isEnd = false;
        if (fieldsToCheck[0][0] && fieldsToCheck[0][1] && fieldsToCheck[0][2]) {
            isEnd = true;
            imageField11.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField12.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField13.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[1][0] && fieldsToCheck[1][1] && fieldsToCheck[1][2]) {
            isEnd = true;
            imageField21.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField22.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField23.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[2][0] && fieldsToCheck[2][1] && fieldsToCheck[2][2]) {
            isEnd = true;
            imageField31.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField32.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField33.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[0][0] && fieldsToCheck[1][0] && fieldsToCheck[2][0]) {
            isEnd = true;
            imageField11.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField21.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField31.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[0][1] && fieldsToCheck[1][1] && fieldsToCheck[2][1]) {
            isEnd = true;
            imageField12.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField22.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField32.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[0][2] && fieldsToCheck[1][2] && fieldsToCheck[2][2]) {
            isEnd = true;
            imageField13.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField23.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField33.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[0][0] && fieldsToCheck[1][1] && fieldsToCheck[2][2]) {
            isEnd = true;
            imageField11.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField22.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField33.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (fieldsToCheck[0][2] && fieldsToCheck[1][1] && fieldsToCheck[2][0]) {
            isEnd = true;
            imageField13.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField22.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            imageField31.setImageTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else if (steps >= 9) {
            isTie = true;
            isEnd = true;
        }

        return isEnd;
    }

    private void endGame() {
        imageField11.setEnabled(false);
        imageField12.setEnabled(false);
        imageField13.setEnabled(false);
        imageField21.setEnabled(false);
        imageField22.setEnabled(false);
        imageField23.setEnabled(false);
        imageField31.setEnabled(false);
        imageField32.setEnabled(false);
        imageField33.setEnabled(false);

        String textWin = "";
        if (isTie) {
            textWin = getString(R.string.tied_game);
        } else {
            switch (turn) {
                case CIRCLE:
                    circlePoints++;
                    textPlayerCirclePoints.setText(String.valueOf(circlePoints));
                    textWin = getString(R.string.circles_wins);
                    break;
                case CROSS:
                    crossPoints++;
                    textPlayerCrossPoints.setText(String.valueOf(crossPoints));
                    textWin = getString(R.string.crosses_wins);
                    break;
            }
        }
        textGameEnded.setText(textWin);
        textGameEnded.setVisibility(View.VISIBLE);
        buttonPlayAgain.setVisibility(View.VISIBLE);

    }
}
