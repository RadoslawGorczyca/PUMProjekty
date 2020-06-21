package org.gorczyca.pum.project3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener,
        View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 1;
    boolean isDummyFileCreated = false;
    private TextToSpeech tts;
    private EditText editTextToRead;
    private Button buttonRead;
    private SeekBar seekReadingSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_3_text_to_speech);
        setTitle(R.string.text_to_speech);
        tts = new TextToSpeech(this, this);
        bindIds();
        bindListeners();
        getPermissionForExternalFilesReadAndWrite();
    }

    private void bindIds() {
        editTextToRead = findViewById(R.id.edit_text_to_read);
        buttonRead = findViewById(R.id.button_read);
        seekReadingSpeed = findViewById(R.id.seek_reading_speed);
    }

    private void bindListeners() {
        buttonRead.setOnClickListener(this);
        seekReadingSpeed.setMax(20);
        seekReadingSpeed.setProgress(10);
        seekReadingSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tts.setSpeechRate((float) progress / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void readText() {
        String text = editTextToRead.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "p3tts");
    }

    private void loadTextFromFile() {
        if (isDummyFileCreated) {
            String ret = "";

            try {

                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(getFilesDir() + File.separator + "tts", "text.txt")));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                receiveString = bufferedReader.readLine();
                while (receiveString != null) {
                    stringBuilder.append(receiveString);
                    receiveString = bufferedReader.readLine();
                    if(receiveString != null){
                        stringBuilder.append("\n");
                    }
                }

                inputStreamReader.close();
                ret = stringBuilder.toString();
            } catch (FileNotFoundException e) {
                Log.e("tts", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("tts", "Can not read file: " + e.toString());
            }

            editTextToRead.setText(ret);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_failed_to_create_dummy_file)
                    .setPositiveButton(android.R.string.ok, null)
                    .setCancelable(false)
                    .show();
        }
    }

    private void getPermissionForExternalFilesReadAndWrite() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);
        } else {
            createDummyTextFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            getPermissionForExternalFilesReadAndWrite();
        } else {
            createDummyTextFile();
        }
    }

    private void createDummyTextFile() {
        try {
            File file = new File(getFilesDir() + File.separator + "tts");

            if (!file.exists()) {
                file.mkdir();
            }
            File textFile = new File(file, "text.txt");
            FileWriter writer = new FileWriter(textFile);
            writer.append("Hi!\nThis file was created to test out the file reading capability of Radek's app.\nI hope it still does work for you as well, professor!\nHave a nice day! :)");
            Log.d("TTS", textFile.getAbsolutePath());
            writer.flush();
            writer.close();
            isDummyFileCreated = true;
        } catch (IOException e) {
            Log.e("tts", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_3_text_to_speech, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_load_file) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.read_from_file)
                    .setMessage(R.string.dialog_message_read_text_from_file)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> loadTextFromFile())
                    .setNegativeButton(android.R.string.cancel, null)
                    .setCancelable(false)
                    .show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, getString(R.string.TTS_language_not_supported), Toast.LENGTH_SHORT).show();
            } else {
                buttonRead.setEnabled(true);
                readText();
            }
        } else {
            Toast.makeText(this, getString(R.string.TTS_initialization_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonRead.getId()) {
            readText();
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
