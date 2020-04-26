package org.gorczyca.pum.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.gorczyca.pum.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuadraticFunctionsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputA;
    private EditText inputB;
    private EditText inputC;
    private Button buttonCount;
    private TextView textResults;
    private TextView textNotQuadraticFunction;
    private TextView textFunction;
    private TextView textDelta;
    private TextView textLabelZeroPoints;
    private TextView textNoZeroPoints;
    private TextView textX0;
    private TextView textX1;
    private TextView textX2;
    private TextView textLabelPeakPoint;
    private TextView textP;
    private TextView textQ;
    private TextView textW;
    private TextView textLabelYCross;
    private TextView textF0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_1_quadratic_functions);
        setTitle(R.string.quadratic_functions);
        boundIds();
        setListeners();
        setUItoStartingPoint();
    }

    private void boundIds() {
        inputA = findViewById(R.id.input_a);
        inputB = findViewById(R.id.input_b);
        inputC = findViewById(R.id.input_c);
        buttonCount = findViewById(R.id.button_count);
        textResults = findViewById(R.id.text_results);
        textNotQuadraticFunction = findViewById(R.id.text_not_quadratic_function);
        textFunction = findViewById(R.id.text_function);
        textDelta = findViewById(R.id.delta);
        textLabelZeroPoints = findViewById(R.id.text_zero_points);
        textNoZeroPoints = findViewById(R.id.text_no_zero_points);
        textX0 = findViewById(R.id.text_x0);
        textX1 = findViewById(R.id.text_x1);
        textX2 = findViewById(R.id.text_x2);
        textLabelPeakPoint = findViewById(R.id.text_peak);
        textP = findViewById(R.id.text_p);
        textQ = findViewById(R.id.text_q);
        textW = findViewById(R.id.text_peak_point);
        textLabelYCross = findViewById(R.id.text_y_cross);
        textF0 = findViewById(R.id.text_y_cross_point);
    }

    private void setListeners() {
        buttonCount.setOnClickListener(this);
        inputC.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    onClick(buttonCount);
                    return true;
                }
                return false;
            }
        });
    }

    private void setUItoStartingPoint() {
        textResults.setVisibility(View.GONE);
        textNotQuadraticFunction.setVisibility(View.GONE);
        textFunction.setVisibility(View.GONE);
        textDelta.setVisibility(View.GONE);
        textLabelZeroPoints.setVisibility(View.GONE);
        textNoZeroPoints.setVisibility(View.GONE);
        textX0.setVisibility(View.GONE);
        textX1.setVisibility(View.GONE);
        textX2.setVisibility(View.GONE);
        textLabelPeakPoint.setVisibility(View.GONE);
        textP.setVisibility(View.GONE);
        textQ.setVisibility(View.GONE);
        textW.setVisibility(View.GONE);
        textLabelYCross.setVisibility(View.GONE);
        textF0.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonCount.getId()) {
            boolean isACorrect = checkIfInputCorrect(inputA);
            boolean isBCorrect = checkIfInputCorrect(inputB);
            boolean isCCorrect = checkIfInputCorrect(inputC);

            if (isACorrect && isBCorrect && isCCorrect) {
                setUItoStartingPoint();
                double a = getInputNumber(inputA);
                double b = getInputNumber(inputB);
                double c = getInputNumber(inputC);
                countQuadraticFunction(a, b, c);
            } else {
                if (!isACorrect) inputA.setError(getString(R.string.error_insert_correct_number));
                if (!isBCorrect) inputB.setError(getString(R.string.error_insert_correct_number));
                if (!isCCorrect) inputC.setError(getString(R.string.error_insert_correct_number));
            }
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

    private double getInputNumber(EditText inputNumber) {
        double number = Double.parseDouble(inputNumber.getText().toString());
        inputNumber.setText("");
        return number;
    }

    private void countQuadraticFunction(double a, double b, double c) {
        double x0;
        double x1;
        double x2;
        double p;
        double q;

        textResults.setVisibility(View.VISIBLE);
        textFunction.setText(String.format(getString(R.string.quadratic_function), a, b, c));
        textFunction.setVisibility(View.VISIBLE);

        if (a == 0) {
            textNotQuadraticFunction.setVisibility(View.VISIBLE);
        } else {

            double delta = b * b - 4 * a * c;
            textDelta.setText(String.format(getString(R.string.delta), a, b, c, delta));
            textDelta.setVisibility(View.VISIBLE);

            textLabelZeroPoints.setVisibility(View.VISIBLE);

            if (delta > 0) {
                x1 = (-b + Math.sqrt(delta)) / (2 * a);
                x2 = (-b - Math.sqrt(delta)) / (2 * a);

                textX1.setText(String.format(getString(R.string.x1), a, b, x1));
                textX2.setText(String.format(getString(R.string.x2), a, b, x2));
                textX1.setVisibility(View.VISIBLE);
                textX2.setVisibility(View.VISIBLE);
            } else if (delta == 0) {
                x0 = -b / (2 * a);
                textX0.setText(String.format(getString(R.string.x0), a, b, x0));
                textX0.setVisibility(View.VISIBLE);
            } else {
                textNoZeroPoints.setVisibility(View.VISIBLE);
            }

            textLabelPeakPoint.setVisibility(View.VISIBLE);

            p = -b / (2 * a);
            q = -delta / (4 * a);
            textP.setText(String.format(getString(R.string.p), a, b, p));
            textQ.setText(String.format(getString(R.string.q), a, delta, q));
            textW.setText(String.format(getString(R.string.w), p, q));
            textP.setVisibility(View.VISIBLE);
            textQ.setVisibility(View.VISIBLE);
            textW.setVisibility(View.VISIBLE);

            textLabelYCross.setVisibility(View.VISIBLE);
            textF0.setText(String.format(getString(R.string.f0), c));
            textF0.setVisibility(View.VISIBLE);
        }
    }
}
