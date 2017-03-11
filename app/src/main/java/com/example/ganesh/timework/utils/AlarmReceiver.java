package com.example.ganesh.timework.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.ganesh.timework.LandingPageActivity;
import com.example.ganesh.timework.R;
import com.example.ganesh.timework.data.DatabaseContract;

/**
 * Created by Ganesh Prasad on 23-02-2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final int ROUTINE_NOTIFICATION_ID = 1001;
    private static final String LOG_TAG = "Alarm receiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(LOG_TAG, "Inside onReceive");
        Intent serviceIntent = new Intent( context, CallNotificationService.class);

        int Id = intent.getIntExtra( NotifyRoutineOrTask.DB_ID , -1 );

//        DB call
        Uri uri = DatabaseContract.RoutineContract.buildRoutineUri(Id);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        assert cursor != null;
        Routines routines = null;
        if (cursor.moveToFirst()) {
            routines = Routines.getRoutinesFromCursor(cursor);
        }

        assert routines != null;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                notificationBuilder.setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(routines.getRoutineName())
                        .setContentText(routines.getRoutineType());

        Intent alarmIntent = new Intent(context, LandingPageActivity.class);

        TaskStackBuilder taskBuilder = TaskStackBuilder.create(context);
        taskBuilder.addNextIntent(alarmIntent);
        PendingIntent pi = taskBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pi);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(ROUTINE_NOTIFICATION_ID, notificationBuilder.build());

    }
}
