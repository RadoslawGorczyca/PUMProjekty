package org.gorczyca.pum.projectEnd;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ToDoItemDetailsActivity extends AppCompatActivity {

    private String toDoItemId;
    private ToDoItem toDoItem;

    private TextView textName;
    private TextView textIsDone;
    private TextView textIsHighPriority;
    private TextView textCreateDate;
    private TextView textDueDate;
    private TextView textEndDate;
    private ListView listViewAttachments;
    private AttachmentsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_end_to_do_item_details);
        setTitle(R.string.to_do_item_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            toDoItemId = bundle.getString(Constants.INTENT_KEY_TO_DO_ITEM_DETAILS);
        } else {
            finish();
        }

        bindIds();
        setUI();
    }

    private void bindIds() {
        textIsDone = findViewById(R.id.text_is_done);
        textName = findViewById(R.id.text_name);
        textIsHighPriority = findViewById(R.id.text_is_high_priority);
        textCreateDate = findViewById(R.id.text_create_date);
        textDueDate = findViewById(R.id.text_due_date);
        textEndDate = findViewById(R.id.text_end_date);
        listViewAttachments = findViewById(R.id.list_attachments);

    }

    private void setUI() {
        toDoItem = ToDoMainActivity.toDoItemsViewModel.getToDoItemById(toDoItemId);
        textIsDone.setText(String.format(getString(R.string.is_done), toDoItem.isDone() ? getString(R.string.yes) : getString(R.string.no)));
        textName.setText(String.format(getString(R.string.name), toDoItem.getName()));
        textIsHighPriority.setText(String.format(getString(R.string.is_high_priority), toDoItem.isHighPriority() ? getString(R.string.yes) : getString(R.string.no)));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(toDoItem.getCreateDateMillis());
        textCreateDate.setText(String.format(getString(R.string.details_create_date), new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(calendar.getTime())));
        calendar.setTimeInMillis(toDoItem.getDueDateMillis());
        textDueDate.setText(String.format(getString(R.string.details_due_date), new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(calendar.getTime())));
        if(toDoItem.isDone()){
            calendar.setTimeInMillis(toDoItem.getEndDateMillis());
            textEndDate.setText(String.format(getString(R.string.details_end_date), new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(calendar.getTime())));
        } else {
            textEndDate.setText(String.format(getString(R.string.details_end_date), "(Brak)"));
        }
        adapter = new AttachmentsListAdapter(this, R.layout.layout_to_do_attachment, toDoItem.getAttachments(), false);
        listViewAttachments.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_end_to_do_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intentEdit = new Intent(this, ToDoAddOrEditActivity.class);
                intentEdit.putExtra(Constants.TO_DO_EDITOR_ACTIVITY_MODE, Constants.TO_DO_EDITOR_MODE_EDITING);
                intentEdit.putExtra(Constants.INTENT_KEY_TO_DO_ITEM_TO_EDIT, toDoItemId);
                this.startActivity(intentEdit);
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            ToDoMainActivity.toDoItemsViewModel.delete(toDoItem);
                            finish();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUI();
    }
}
