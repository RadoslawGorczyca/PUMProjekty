package org.gorczyca.pum.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class EnglishLessonTestActivity extends AppCompatActivity {

    private final int LANGUAGE_ENGLISH = 1;
    private final int LANGUAGE_POLISH = 2;
    private final int MODE_LEARNING = 1;
    private final int MODE_TEST = 2;
    private final String EXTRA_LANGUAGE = "language";
    private final String EXTRA_MODE = "mode";
    private final String EXTRA_POINTS = "points";
    private final String EXTRA_WORDS_COUNT = "wordsCount";

    private EditText editFirstWord;
    private EditText editSecondWord;

    private int language;
    private int mode;
    private int wordIndex = -1;
    private int points = 0;

    private ArrayList<DictionaryItem> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_5_english_lesson_test);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            language = bundle.getInt(EXTRA_LANGUAGE);
            mode = bundle.getInt(EXTRA_MODE);
        } else {
            finish();
        }
        prepareTest();
        bindIds();
        nextWord();
    }

    private void prepareTest() {
        dictionary = new ArrayList<>();
        dictionary.add(new DictionaryItem("one", "jeden"));
        dictionary.add(new DictionaryItem("two", "dwa"));
        dictionary.add(new DictionaryItem("three", "trzy"));
        dictionary.add(new DictionaryItem("four", "cztery"));
        dictionary.add(new DictionaryItem("five", "pięć"));
        dictionary.add(new DictionaryItem("six", "sześć"));
        dictionary.add(new DictionaryItem("seven", "siedem"));
        dictionary.add(new DictionaryItem("eight", "osiem"));
        dictionary.add(new DictionaryItem("nine", "dziewięć"));
        dictionary.add(new DictionaryItem("ten", "dziesięć"));
        Collections.shuffle(dictionary);
    }

    private void bindIds() {
        editFirstWord = findViewById(R.id.edit_first_word);
        editSecondWord = findViewById(R.id.edit_second_word);
    }

    private void nextWord() {
        wordIndex++;
        if (wordIndex < dictionary.size()) {
            String modeString = mode == MODE_TEST ? getString(R.string.test) : getString(R.string.learning);
            setTitle(String.format(Locale.getDefault(), modeString + " - %1$d/%2$d", wordIndex + 1, dictionary.size()));
            printFirstWord();
            printSecondWord();
        } else {
            if (mode == MODE_TEST) {
                goToResults();
            } else {
                Toast.makeText(this, getString(R.string.learning_finished), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void printFirstWord() {
        if (language == LANGUAGE_ENGLISH) {
            editFirstWord.setText(dictionary.get(wordIndex).getPolishWord());
        } else {
            editFirstWord.setText(dictionary.get(wordIndex).getEnglishWord());
        }
    }

    private void printSecondWord() {
        if (mode == MODE_LEARNING) {
            editSecondWord.setEnabled(false);
            if (language == LANGUAGE_ENGLISH) {
                editSecondWord.setText(dictionary.get(wordIndex).getEnglishWord());
            } else {
                editSecondWord.setText(dictionary.get(wordIndex).getPolishWord());
            }
        } else {
            editSecondWord.setEnabled(true);
        }
    }

    private boolean checkIsCorrect() {
        String userInput = editSecondWord.getText().toString().trim().toLowerCase();
        editSecondWord.setText("");
        String correctWord;
        if (language == LANGUAGE_ENGLISH) {
            correctWord = dictionary.get(wordIndex).getEnglishWord().toLowerCase();
        } else {
            correctWord = dictionary.get(wordIndex).getPolishWord().toLowerCase();
        }
        return userInput.equals(correctWord);
    }

    private void goToResults() {
        Intent intent = new Intent(this, EnglishLessonResultsActivity.class);
        intent.putExtra(EXTRA_POINTS, points);
        intent.putExtra(EXTRA_WORDS_COUNT, dictionary.size());
        startActivity(intent);
        finish();
    }

    private void confirmEndTest() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.end_test)
                .setMessage(R.string.end_test_confirmation_message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (mode == MODE_LEARNING) {
                        finish();
                    } else {
                        goToResults();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_5_english_lesson_test, menu);
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
        } else if (item.getItemId() == R.id.action_next) {
            if (mode == MODE_TEST && checkIsCorrect()) {
                points++;
            }
            nextWord();
            return true;
        } else if (item.getItemId() == R.id.action_end_test) {
            confirmEndTest();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        confirmEndTest();
    }
}
