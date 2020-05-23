package org.gorczyca.pum.projectEnd;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Comparator;
import java.util.UUID;

/**
 * Created by: Radosław Gorczyca
 * 22.05.2020 23:15
 */
@Entity(tableName = "toDoItem")
class ToDoItem {

    @PrimaryKey()
    @ColumnInfo(name = "uid")
    @NonNull
    private String uuid;

    @ColumnInfo(name = "isDone")
    private boolean isDone;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "isHighPriority")
    private boolean isHighPriority;

    @ColumnInfo(name = "createDateMillis")
    private long createDateMillis;

    @ColumnInfo(name = "endDateMillis")
    private long endDateMillis;

    @ColumnInfo(name = "dueDateMillis")
    private long dueDateMillis;



    public ToDoItem(@NonNull String name, boolean isHighPriority, long createDateMillis, long dueDateMillis) {
        this.name = name;
        this.isHighPriority = isHighPriority;
        this.createDateMillis = createDateMillis;
        this.dueDateMillis = dueDateMillis;
        this.isDone = false;
        uuid = UUID.randomUUID().toString();
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHighPriority() {
        return isHighPriority;
    }

    public void setHighPriority(boolean highPriority) {
        isHighPriority = highPriority;
    }

    public long getCreateDateMillis() {
        return createDateMillis;
    }

    public void setCreateDateMillis(long createDateMillis) {
        this.createDateMillis = createDateMillis;
    }

    public long getEndDateMillis() {
        return endDateMillis;
    }

    public void setEndDateMillis(long endDateMillis) {
        this.endDateMillis = endDateMillis;
    }

    public long getDueDateMillis() {
        return dueDateMillis;
    }

    public void setDueDateMillis(long dueDateMillis) {
        this.dueDateMillis = dueDateMillis;
    }


    public static Comparator<ToDoItem> ToDoItemCreateDateComparator = (o1, o2) -> {
        Calendar toDoItemCreateDate1 = Calendar.getInstance();
        Calendar toDoItemCreateDate2 = Calendar.getInstance();

        toDoItemCreateDate1.setTimeInMillis(o1.getCreateDateMillis());
        toDoItemCreateDate2.setTimeInMillis(o2.getCreateDateMillis());

        return  toDoItemCreateDate1.compareTo(toDoItemCreateDate2);
    };

    public static Comparator<ToDoItem> ToDoItemEndDateComparator = (o1, o2) -> {
        Calendar toDoItemEndDate1 = Calendar.getInstance();
        Calendar toDoItemEndDate2 = Calendar.getInstance();

        toDoItemEndDate1.setTimeInMillis(o1.getEndDateMillis());
        toDoItemEndDate2.setTimeInMillis(o2.getEndDateMillis());

        return  toDoItemEndDate1.compareTo(toDoItemEndDate2);
    };

    public static Comparator<ToDoItem> ToDoItemPriorityComparator = (o1, o2) -> {
        boolean isToDoItemHighPriority1 = o1.isHighPriority();
        boolean isToDoItemHighPriority2 = o2.isHighPriority();

        return Boolean.compare(isToDoItemHighPriority1, isToDoItemHighPriority2);
    };

    public static Comparator<ToDoItem> ToDoItemNameComparator = (o1, o2) -> {
        String toDoItemName1 = o1.getName().toUpperCase();
        String toDoItemName2 = o2.getName().toUpperCase();

        return toDoItemName1.compareTo(toDoItemName2);
    };

    public static Comparator<ToDoItem> ToDoItemStatusComparator = (o1, o2) -> {
        boolean isToDoItemDone1 = o1.isDone();
        boolean isToDoItemDone2 = o2.isDone();

        return Boolean.compare(isToDoItemDone1, isToDoItemDone2);
    };
}
