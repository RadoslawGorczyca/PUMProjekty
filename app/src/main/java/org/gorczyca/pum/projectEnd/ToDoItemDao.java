package org.gorczyca.pum.projectEnd;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 23.05.2020 10:10
 */
@Dao
public interface ToDoItemDao {
    @Query("SELECT * FROM todoitem")
    LiveData<List<ToDoItem>> getAllToDoItems();

    @Query("SELECT * FROM todoitem WHERE uid = :id LIMIT 1")
    ToDoItem findToDoItemById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToDoItem toDoItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToDoItem... toDoItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ToDoItem toDoItem);

    @Delete
    void delete(ToDoItem toDoItem);

    @Query("DELETE FROM todoitem")
    void deleteAll();
}
