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
    List<ToDoItem> getAllToDoItems();

    @Query("SELECT * FROM todoitem ORDER BY createDateMillis DESC")
    List<ToDoItem> getAllToDoItemsOrderByCreateDate();

    @Query("SELECT * FROM todoitem ORDER BY endDateMillis DESC")
    List<ToDoItem> getAllToDoItemsOrderByEndDate();

    @Query("SELECT * FROM todoitem ORDER BY isHighPriority DESC")
    List<ToDoItem> getAllToDoItemsOrderByPriority();

    @Query("SELECT * FROM todoitem ORDER BY name ASC")
    List<ToDoItem> getAllToDoItemsOrderByName();

    @Query("SELECT * FROM todoitem ORDER BY isDone ASC")
    List<ToDoItem> getAllToDoItemsOrderByStatus();

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
