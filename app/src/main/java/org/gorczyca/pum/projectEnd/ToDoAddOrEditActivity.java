package org.gorczyca.pum.projectEnd;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.gorczyca.pum.R;
import org.gorczyca.pum.utils.Constants;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ToDoAddOrEditActivity extends AppCompatActivity {

    private EditText editToDoItemName;
    private int activityMode;
    private String toDoItemId;
    private ToDoItem itemToEdit;
    private CheckBox checkHighPriority;
    private Button buttonDueDate;
    private Uri fileUri;
    private ListView listViewAttachments;
    private List<String> attachmentsList = new ArrayList<>();
    private AttachmentsListAdapter attachmentsListAdapter;

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
            if (activityMode == Constants.TO_DO_EDITOR_MODE_EDITING) {
                setTitle(R.string.edit_to_do_item);
                toDoItemId = bundle.getString(Constants.INTENT_KEY_TO_DO_ITEM_TO_EDIT);
                setUiForEditing();
            } else if (activityMode == Constants.TO_DO_EDITOR_MODE_ADDING) {
                setTitle(R.string.add_to_do_item);
            } else {
                finish();
            }
        } else {
            finish();
        }
        addAttachmentsListView();
    }

    private void bindIds() {
        editToDoItemName = findViewById(R.id.edit_to_do_item_name);
        checkHighPriority = findViewById(R.id.check_high_priority);
        buttonDueDate = findViewById(R.id.button_due_date);
        listViewAttachments = findViewById(R.id.list_attachments);
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
        attachmentsList = itemToEdit.getAttachments();

    }

    private void addAttachmentsListView() {
        attachmentsListAdapter = new AttachmentsListAdapter(this, R.layout.layout_to_do_attachment_removable, attachmentsList, true);
        listViewAttachments.setAdapter(attachmentsListAdapter);
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

        if (editToDoItemName.getText().toString().trim().isEmpty()) {
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

                if (toDoItemToUpdate.isHighPriority() != isHighPriority) {
                    isChanged = true;
                    toDoItemToUpdate.setHighPriority(isHighPriority);
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
                Calendar calendarDueDate = Calendar.getInstance();
                calendarDueDate.setTimeInMillis(toDoItemToUpdate.getDueDateMillis());
                Calendar calendarNewDueDate = Calendar.getInstance();
                try {
                    Date newDueDate = null;
                    newDueDate = simpleDateFormat.parse(buttonDueDate.getText().toString());
                    if (newDueDate != null) {
                        calendarNewDueDate.setTime(newDueDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (calendarDueDate.compareTo(calendarNewDueDate) != 0) {
                    isChanged = true;
                    toDoItemToUpdate.setDueDateMillis(calendarNewDueDate.getTimeInMillis());
                }

                if(toDoItemToUpdate.getAttachments() != attachmentsList){
                    isChanged = true;
                    toDoItemToUpdate.setAttachments(attachmentsList);
                }

                if (isChanged) {
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
                if (date != null) {
                    calendarDueDate.setTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ToDoItem newToDoItem = new ToDoItem(name, isHighPriority, calendarNow.getTimeInMillis(), calendarDueDate.getTimeInMillis());
            newToDoItem.setAttachments(attachmentsList);
            toDoItemsDao.insert(newToDoItem);
        }
    }

    private void openAttachmentPopUpMenu() {
        View view = findViewById(R.id.action_attachment);
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_project_end_to_do_item_attachment_pop_up, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_video:
                    addAttachment(Constants.MEDIA_TYPE_VIDEO);
                    return true;
                case R.id.action_image:
                    addAttachment(Constants.MEDIA_TYPE_IMAGE);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void addAttachment(int mediaType) {
        String title = "";

        switch (mediaType) {
            case Constants.MEDIA_TYPE_IMAGE:
                title = getString(R.string.add_image);
                break;
            case Constants.MEDIA_TYPE_VIDEO:
                title = getString(R.string.add_video);
                break;
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(R.string.from_gallery_or_from_camera)
                .setPositiveButton(R.string.gallery, (dialog, which) -> chooseInGallery(mediaType))
                .setNegativeButton(R.string.camera, (dialog, which) -> captureOnCamera(mediaType))
                .setNeutralButton(R.string.cancel, null)
                .show();

    }

    private void chooseInGallery(int mediaType) {
        if (mediaType == Constants.MEDIA_TYPE_IMAGE) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, Constants.GALLERY_PICK_IMAGE_REQUEST_CODE);
        } else if (mediaType == Constants.MEDIA_TYPE_VIDEO) {
            Intent pickVideo = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickVideo, Constants.GALLERY_PICK_VIDEO_REQUEST_CODE);
        }
    }

    private void captureOnCamera(int mediaType) {
        if (mediaType == Constants.MEDIA_TYPE_IMAGE) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(Constants.MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

        } else if (mediaType == Constants.MEDIA_TYPE_VIDEO) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            fileUri = getOutputMediaFileUri(Constants.MEDIA_TYPE_VIDEO);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, Constants.CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
            case Constants.GALLERY_PICK_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    fileUri = data.getData();
                    if(fileUri != null) {
                        String fileName = new SimpleDateFormat("yyyyMMddHHmmss",
                                Locale.getDefault()).format(new Date());
                        attachmentsList.add(fileName + ".jpg");
                        attachmentsListAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case Constants.GALLERY_PICK_VIDEO_REQUEST_CODE:
            case Constants.CAMERA_CAPTURE_VIDEO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    fileUri = data.getData();
                    if(fileUri != null) {
                        String fileName = new SimpleDateFormat("yyyyMMddHHmmss",
                                Locale.getDefault()).format(new Date());
                        attachmentsList.add(fileName + ".mp4");
                        attachmentsListAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        File file = getOutputMediaFile(type);
        if (file != null) {
            return FileProvider.getUriForFile(this,
                    getApplicationContext().getPackageName() + ".provider",
                    file);
        } else {
            return null;
        }
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory(),
                "files");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("files", "Oops! Failed create "
                        + "files" + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Constants.MEDIA_TYPE_IMAGE) {
            String fileName = "IMG_" + timeStamp + ".jpg";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + fileName);
        } else if (type == Constants.MEDIA_TYPE_VIDEO) {
            String fileName = "VID_" + timeStamp + ".mp4";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + fileName);
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
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
            case R.id.action_attachment:
                openAttachmentPopUpMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
