package org.gorczyca.pum.projectEnd;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ToDoMainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ToDoItemsViewModel toDoItemsViewModel;
    private List<ToDoItem> toDoItemList;
    private RecyclerView recyclerToDoItems;
    private ToDoRecycleAdapter toDoItemArrayAdapter;
    private TextView textNoItems;
    private FloatingActionButton buttonAddToDo;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_end_to_do_main);
        setTitle(R.string.to_do_list);
        bindIds();
        setUI();
        initData(Constants.SORTING_METHOD.STATUS);
        setListeners();
        setAlarm();
    }

    private void bindIds() {
        recyclerToDoItems = findViewById(R.id.list_to_do);
        textNoItems = findViewById(R.id.text_no_items);
        buttonAddToDo = findViewById(R.id.button_add_to_do);
    }

    private void setUI() {
        toDoItemArrayAdapter = new ToDoRecycleAdapter(this);
        recyclerToDoItems.setAdapter(toDoItemArrayAdapter);
        recyclerToDoItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListeners() {
        buttonAddToDo.setOnClickListener(this);
    }

    private void setAlarm() {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 24 * 60 * 60 * 1000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);

        if (manager != null) {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    interval, pendingIntent);
        }

    }

    private void addToDoItem() {
        Intent intent = new Intent(this, ToDoAddOrEditActivity.class);
        intent.putExtra(Constants.TO_DO_EDITOR_ACTIVITY_MODE, Constants.TO_DO_EDITOR_MODE_ADDING);
        startActivity(intent);
    }

    private void initData(Constants.SORTING_METHOD method) {
        toDoItemsViewModel = ViewModelProviders.of(this).get(ToDoItemsViewModel.class);
        toDoItemArrayAdapter = new ToDoRecycleAdapter(this);
        recyclerToDoItems.setAdapter(toDoItemArrayAdapter);

        switch (method){
            case CREATE_DATE:
                toDoItemList = toDoItemsViewModel.getToDoItemsListOrderByCreateDate();
                break;
            case END_DATE:
                toDoItemList = toDoItemsViewModel.getToDoItemsListOrderByEndDate();
                break;
            case PRIORITY:
                toDoItemList = toDoItemsViewModel.getToDoItemsListOrderByPriority();
                break;
            case NAME:
                toDoItemList = toDoItemsViewModel.getToDoItemsListOrderByName();
                break;
            case STATUS:
                toDoItemList = toDoItemsViewModel.getToDoItemsListOrderByStatus();
                break;
            default:
                toDoItemList = toDoItemsViewModel.getToDoItemsList();
        }

        toDoItemArrayAdapter.setToDoItemsList(toDoItemList);
        showOrHideTextNoItem();
    }

    private void saveToDoItemsToFile() {
        String toDoString = toDoItemsToString();
        if (!toDoString.isEmpty()) {
            File path = getExternalFilesDir(null);
            File file = new File(path, "ToDoList.txt");
            FileOutputStream stream;
            try {
                stream = new FileOutputStream(file);
                stream.write(toDoString.getBytes());
                stream.close();
                Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = FileProvider.getUriForFile(this,
                            getApplicationContext().getPackageName() + ".provider",
                            file);
                    intent.setData(uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // no Activity to handle this kind of files
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Brak zadań do wygenerowania pliku!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrHideTextNoItem() {
        if (toDoItemsViewModel.getToDoItemsList() != null) {
            textNoItems.setVisibility(toDoItemsViewModel.getToDoItemsList().size() > 0 ? View.GONE : View.VISIBLE);
        } else {
            textNoItems.setVisibility(View.VISIBLE);
        }
    }

    private String toDoItemsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (toDoItemList != null && toDoItemList.size() > 0) {
            for (ToDoItem item : toDoItemList) {
                stringBuilder.append("Nazwa: ").append(item.getName()).append("\n");
                stringBuilder.append("Zakończone: ").append(item.isDone() ? "Tak" : "Nie").append("\n");
                stringBuilder.append("Wysoki priorytet: ").append(item.isHighPriority() ? "Tak" : "Nie").append("\n");
                stringBuilder.append("\n\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_end_to_do_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                openSortingPopUpMenu();
                return true;
            case R.id.action_save_to_file:
                saveToDoItemsToFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSortingPopUpMenu() {
        View view = findViewById(R.id.action_sort);
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_project_end_to_do_sort_options_pop_up, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.action_sort_by_create_date:
                    sortList(Constants.SORTING_METHOD.CREATE_DATE);
                    return true;
                case R.id.action_sort_by_end_date:
                    sortList(Constants.SORTING_METHOD.END_DATE);
                    return true;
                case R.id.action_sort_by_priority:
                    sortList(Constants.SORTING_METHOD.PRIORITY);
                    return true;
                case R.id.action_sort_by_name:
                    sortList(Constants.SORTING_METHOD.NAME);
                    return true;
                case R.id.action_sort_by_status:
                    sortList(Constants.SORTING_METHOD.STATUS);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void sortList(Constants.SORTING_METHOD method){
        if(toDoItemsViewModel.getToDoItemsList() != null) {
            switch (method) {
                case CREATE_DATE:
                    initData(Constants.SORTING_METHOD.CREATE_DATE);
                    break;
                case END_DATE:
                    initData(Constants.SORTING_METHOD.END_DATE);
                    break;
                case PRIORITY:
                    initData(Constants.SORTING_METHOD.PRIORITY);
                    break;
                case NAME:
                    initData(Constants.SORTING_METHOD.NAME);
                    break;
                case STATUS:
                    initData(Constants.SORTING_METHOD.STATUS);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        addToDoItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(Constants.SORTING_METHOD.STATUS);
    }
}
