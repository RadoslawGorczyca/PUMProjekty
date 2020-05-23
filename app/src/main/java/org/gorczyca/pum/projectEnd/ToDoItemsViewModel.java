package org.gorczyca.pum.projectEnd;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 23.05.2020 10:25
 */
public class ToDoItemsViewModel extends AndroidViewModel {
    private ToDoItemDao toDoItemDao;
    private List<ToDoItem> toDoItemsLiveData;
    public ToDoItemsViewModel(@NonNull Application application) {
        super(application);
        toDoItemDao = ToDoItemsDatabase.getDatabase(application).toDoItemDao();
        toDoItemsLiveData = toDoItemDao.getAllToDoItems();
    }
    public List<ToDoItem> getToDoItemsList() {
        return toDoItemsLiveData;
    }

    public List<ToDoItem> getToDoItemsListOrderByCreateDate(){
        toDoItemsLiveData = toDoItemDao.getAllToDoItemsOrderByCreateDate();
        return toDoItemsLiveData;
    }

    public List<ToDoItem> getToDoItemsListOrderByEndDate(){
        toDoItemsLiveData = toDoItemDao.getAllToDoItemsOrderByEndDate();
        return toDoItemsLiveData;
    }

    public List<ToDoItem> getToDoItemsListOrderByPriority(){
        toDoItemsLiveData = toDoItemDao.getAllToDoItemsOrderByPriority();
        return toDoItemsLiveData;
    }

    public List<ToDoItem> getToDoItemsListOrderByName(){
        toDoItemsLiveData = toDoItemDao.getAllToDoItemsOrderByName();
        return toDoItemsLiveData;
    }

    public List<ToDoItem> getToDoItemsListOrderByStatus(){
        toDoItemsLiveData = toDoItemDao.getAllToDoItemsOrderByStatus();
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
