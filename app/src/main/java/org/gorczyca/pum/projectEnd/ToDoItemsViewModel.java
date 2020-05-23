package org.gorczyca.pum.projectEnd;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 23.05.2020 10:25
 */
public class ToDoItemsViewModel extends AndroidViewModel {
    private ToDoItemDao toDoItemDao;
    private LiveData<List<ToDoItem>> toDoItemsLiveData;
    public ToDoItemsViewModel(@NonNull Application application) {
        super(application);
        toDoItemDao = ToDoItemsDatabase.getDatabase(application).toDoItemDao();
        toDoItemsLiveData = toDoItemDao.getAllToDoItems();
    }
    public LiveData<List<ToDoItem>> getToDoItemsList() {
        return toDoItemsLiveData;
    }

    public ToDoItem getToDoItemById(String id){
        return toDoItemDao.findToDoItemById(id);
    }

    public void insert(ToDoItem... toDoItems) {
        toDoItemDao.insert(toDoItems);
    }

    public void insert(ToDoItem toDoItem) {
        toDoItemDao.insert(toDoItem);
    }

    public void update(ToDoItem toDoItem) {
        toDoItemDao.update(toDoItem);
    }

    public void delete(ToDoItem item){
        toDoItemDao.delete(item);
    }

    public void deleteAll() {
        toDoItemDao.deleteAll();
    }
}
