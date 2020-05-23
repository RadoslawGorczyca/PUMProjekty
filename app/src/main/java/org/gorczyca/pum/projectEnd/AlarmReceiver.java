package org.gorczyca.pum.projectEnd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import org.gorczyca.pum.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by: Radosław Gorczyca
 * 23.05.2020 15:56
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToDoItemDao toDoItemDao = ToDoItemsDatabase.getDatabase(context).toDoItemDao();
        List<ToDoItem> toDoItemList = toDoItemDao.getAllToDoItems().getValue();
        if(toDoItemList != null && toDoItemList.size() > 0){
            StringBuilder toDoItemsStringBuilder = new StringBuilder();
            Calendar calendarToday = Calendar.getInstance();
            Calendar calendarToDoItem = Calendar.getInstance();
            for (ToDoItem item: toDoItemList) {
                calendarToDoItem.setTimeInMillis(item.getDueDateMillis());
                boolean sameDay = calendarToday.get(Calendar.DAY_OF_YEAR) == calendarToDoItem.get(Calendar.DAY_OF_YEAR) &&
                        calendarToday.get(Calendar.YEAR) == calendarToDoItem.get(Calendar.YEAR);
                boolean toDoTaskLate = calendarToday.after(calendarToDoItem);
                boolean isDone = item.isDone();
                if((sameDay || toDoTaskLate) && !isDone){
                    toDoItemsStringBuilder.append(item.getName()).append("\n");
                }
            }

            String message = "Dzisiejsze lub zaległe zadania:\n" + toDoItemsStringBuilder.toString();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = context.getString(R.string.to_do_list);
            int importance = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                importance = NotificationManager.IMPORTANCE_HIGH;
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(mChannel);
                }
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getString(R.string.to_do_list))
                    .setContentText(message);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            if (notificationManager != null) {
                notificationManager.notify(notificationId, mBuilder.build());
            }
        }
    }
}
