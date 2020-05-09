package org.gorczyca.pum.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by: Radosław Gorczyca
 * 09.05.2020 13:55
 */
public class QuizQuestionItem {

    private Drawable questionDrawable;
    private String[] answers;
    private int correctAnswerId;

    public QuizQuestionItem(Drawable questionDrawable, String[] answers, int correctAnswerId) {
        this.questionDrawable = questionDrawable;
        this.answers = answers;
        this.correctAnswerId = correctAnswerId;
    }

    public Drawable getQuestionDrawable() {
        return questionDrawable;
    }

    public void setQuestionDrawable(Drawable questionDrawable) {
        this.questionDrawable = questionDrawable;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }
}
