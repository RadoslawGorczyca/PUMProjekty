package org.gorczyca.pum.projectEnd;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ToDoAddOrEditActivity extends AppCompatActivity {

    private EditText editToDoItemName;
    private int activityMode;
    private String toDoItemId;
    private ToDoItem itemToEdit;
    private CheckBox checkHighPriority;
    private Button buttonDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_end_to_do_add_or_edit);

        bindIds();
        bindListeners();
        setUIForAddingNewToDoItem();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            activityMode = bundle.getInt(Constants.TO_DO_EDITOR_ACTIVITY_MODE);
            if(activityMode == Constants.TO_DO_EDITOR_MODE_EDITING){
                setTitle(R.string.edit_to_do_item);
                toDoItemId = bundle.getString(Constants.INTENT_KEY_TO_DO_ITEM_TO_EDIT);
                setUiForEditing();
            } else if(activityMode == Constants.TO_DO_EDITOR_MODE_ADDING) {
                setTitle(R.string.add_to_do_item);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void bindIds() {
        editToDoItemName = findViewById(R.id.edit_to_do_item_name);
        checkHighPriority = findViewById(R.id.check_high_priority);
        buttonDueDate = findViewById(R.id.button_due_date);
    }

    private void bindListeners() {
        Calendar calendarDueDate = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener time = (view1, hourOfDay, minute) -> {
            calendarDueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendarDueDate.set(Calendar.MINUTE, minute);
            calendarDueDate.set(Calendar.SECOND, 0);
            updateDueDate(calendarDueDate);
        };

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            calendarDueDate.set(Calendar.YEAR, year);
            calendarDueDate.set(Calendar.MONTH, monthOfYear);
            calendarDueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(this, time, calendarDueDate.get(Calendar.HOUR_OF_DAY), calendarDueDate.get(Calendar.MINUTE), true).show();
        };

        buttonDueDate.setOnClickListener(v -> {
            new DatePickerDialog(this, date, calendarDueDate
                    .get(Calendar.YEAR), calendarDueDate.get(Calendar.MONTH),
                    calendarDueDate.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateDueDate(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        String dateString = simpleDateFormat.format(calendar.getTime());
        buttonDueDate.setText(dateString);
    }

    private void setUIForAddingNewToDoItem() {
        Calendar calendarDueDate = Calendar.getInstance();
        String dueDateString = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(calendarDueDate.getTime());
        buttonDueDate.setText(dueDateString);
    }

    private void setUiForEditing() {
        itemToEdit = ToDoMainActivity.toDoItemsViewModel.getToDoItemById(toDoItemId);
        editToDoItemName.setText(itemToEdit.getName());
        checkHighPriority.setChecked(itemToEdit.isHighPriority());
        Calendar calendarDueDate = Calendar.getInstance();
        calendarDueDate.setTimeInMillis(itemToEdit.getDueDateMillis());
        String dueDateString = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(calendarDueDate.getTime());
        buttonDueDate.setText(dueDateString);

    }

    private void addOrEditToDoItem() {
        if (validateFields()) {
            String name = editToDoItemName.getText().toString().trim();
            boolean isHighPriority = checkHighPriority.isChecked();
            saveToDoItem(name, isHighPriority);
            finish();
        }
    }

    private boolean validateFields() {
        boolean isCorrect = true;

        if(editToDoItemName.getText().toString().trim().isEmpty()){
            isCorrect = false;
            editToDoItemName.setError(getString(R.string.error_fields_cannot_be_empty));
            editToDoItemName.requestFocus();
        }

        return isCorrect;
    }

    private void saveToDoItem(String name, boolean isHighPriority) {
        ToDoItemDao toDoItemsDao = ToDoItemsDatabase.getDatabase(this).toDoItemDao();
        if (itemToEdit != null) {
            ToDoItem toDoItemToUpdate = toDoItemsDao.findToDoItemById(itemToEdit.getUuid());
            if (toDoItemToUpdate != null) {

                boolean isChanged = false;
                if (!toDoItemToUpdate.getName().equals(name)) {
                    isChanged = true;
                    toDoItemToUpdate.setName(name);
                }

                if(toDoItemToUpdate.isHighPriority() != isHighPriority){
                    isChanged = true;
                    toDoItemToUpdate.setHighPriority(isHighPriority);
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
                Calendar calendarDueDate = Calendar.getInstance();
                calendarDueDate.setTimeInMillis(itemToEdit.getDueDateMillis());
                Calendar calendarNewDueDate = Calendar.getInstance();
                try {
                    Date newDueDate = null;
                    newDueDate = simpleDateFormat.parse(buttonDueDate.getText().toString());
                    if(newDueDate != null) {
                        calendarNewDueDate.setTime(newDueDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(calendarDueDate.compareTo(calendarNewDueDate) != 0){
                    isChanged = true;
                    itemToEdit.setDueDateMillis(calendarNewDueDate.getTimeInMillis());
                }

                if(isChanged) {
                    toDoItemsDao.update(toDoItemToUpdate);
                }
            }
        } else {
            Calendar calendarNow = Calendar.getInstance();
            Calendar calendarDueDate = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
            try {
                Date date = null;
                date = simpleDateFormat.parse(buttonDueDate.getText().toString());
                if(date != null) {
                    calendarDueDate.setTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            toDoItemsDao.insert(new ToDoItem(name, isHighPriority, calendarNow.getTimeInMillis(), calendarDueDate.getTimeInMillis()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_end_to_do_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                addOrEditToDoItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
