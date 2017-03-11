package com.example.ganesh.timework.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ganesh Prasad on 23-02-2017.
 */

public class NotifyRoutineOrTask {

//    No need to instantiate this
    public NotifyRoutineOrTask() {
    }

    private static final String LOG_TAG = "Notify class";
    static final String DB_ID = "db id";
    private static List<Integer> alarmSetRoutines = new ArrayList<>();;

    public static void setRoutineNotificationAlarm(Context context, int dbId){

//        if( !alarmSetRoutines.contains(dbId) ){
            alarmSetRoutines.add(dbId);

            Log.d(LOG_TAG, "Inside setNotifin method");

            Intent intent = new Intent(context, AlarmReceiver.class );
            intent.putExtra(DB_ID, dbId);

            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

//            DB call
            Uri uri = DatabaseContract.RoutineContract.buildRoutineUri(dbId);
            String[] projection = new String[]{DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_HOUR
                    , DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_MINUTES
                    , DatabaseContract.RoutineContract.COLUMN_DAY_MON
                    , DatabaseContract.RoutineContract.COLUMN_DAY_TUE
                    , DatabaseContract.RoutineContract.COLUMN_DAY_WED
                    , DatabaseContract.RoutineContract.COLUMN_DAY_THU
                    , DatabaseContract.RoutineContract.COLUMN_DAY_FRI
                    , DatabaseContract.RoutineContract.COLUMN_DAY_SAT
                    , DatabaseContract.RoutineContract.COLUMN_DAY_SUN};

            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

            assert cursor != null;
            boolean[] daysToRepeat = new boolean[7];
            int hour = -1;
            int minutes = -1;

            if(cursor.moveToFirst()){

                daysToRepeat[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_MON)) == 1;
                daysToRepeat[2] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_TUE)) == 1;
                daysToRepeat[3] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_WED)) == 1;
                daysToRepeat[4] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_THU)) == 1;
                daysToRepeat[5] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_FRI)) == 1;
                daysToRepeat[6] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_SAT)) == 1;
                daysToRepeat[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_SUN)) == 1;

                hour = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_HOUR));
                minutes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_MINUTES));

            }

//            Trigger time
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            if( hour != -1 && minutes != -1 ) {
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minutes);
                c.set(Calendar.SECOND, 0);
            }

            Log.d( LOG_TAG, " Alarm time " + hour + ": " + minutes);

//            Set alarm for different days
            for (int i = 1; i <= daysToRepeat.length; i++ ){
                if( daysToRepeat[i - 1] ){
                    c.set(Calendar.DAY_OF_WEEK, i);
                    Log.d( LOG_TAG, " Alarm time " + c.HOUR_OF_DAY + ": " + c.MINUTE + "| " + c.DAY_OF_WEEK );
                    am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000*60*60*24*7, pi);
                }
            }
            cursor.close();

    }

    public static void removeRoutineNotification(Context context, int dbId){
        if( alarmSetRoutines.contains(dbId) ){
            alarmSetRoutines.remove(dbId);
        }
    }

}
