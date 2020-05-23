package org.gorczyca.pum.projectEnd;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.gorczyca.pum.utils.Converters;

/**
 * Created by: Radosław Gorczyca
 * 23.05.2020 10:19
 */
@Database(entities = {ToDoItem.class}, version = 5)
@TypeConverters(Converters.class)
public abstract class ToDoItemsDatabase extends RoomDatabase {

    private static final String DB_NAME = "toDoItems.db";
    private static ToDoItemsDatabase INSTANCE;

    public static ToDoItemsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoItemsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoItemsDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new ClearAndInitializeDbAsync(INSTANCE).execute();
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new ClearAndInitializeDbAsync(INSTANCE).execute();
        }
    }

    public abstract ToDoItemDao toDoItemDao();

    private static class ClearAndInitializeDbAsync extends AsyncTask<Void, Void, Void> {
        private final ToDoItemDao toDoItemDao;
        public ClearAndInitializeDbAsync(ToDoItemsDatabase instance) {
            toDoItemDao = instance.toDoItemDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            toDoItemDao.deleteAll();
            return null;
        }
    }

}
