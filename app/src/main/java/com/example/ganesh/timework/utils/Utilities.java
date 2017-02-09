package com.example.ganesh.timework.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ganesh Prasad on 08-08-2016.
 */
public class Utilities {

    /**
     * This class is to handle the dialogbox that comes when delete is pressed
     * @param context
     * @return
     */

    public static boolean deleteItemRoutine(final Context context){

        final boolean[] isPositive = new boolean[1];

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                throw new RuntimeException();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete ?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isPositive[0] = true;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isPositive[0] = false;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        AlertDialog dlg = builder.create();
        dlg.show();

        try {
            Looper.loop();
        }catch (RuntimeException re){}

        return isPositive[0];

    }

    public static String formattedTimeForRoutines(int hour, int minutes){

        String formattedTime = "Not working";

        if( hour >= 12 ){
            if( hour == 12 ){
                formattedTime = String.format(Locale.getDefault() , "%02d:%02d PM " ,hour , minutes);
            }else {
                int hourPM = hour % 12;
                Log.d("time shit,", hourPM + " ");
                formattedTime = String.format(Locale.getDefault() , "%02d:%02d PM " ,hourPM , minutes);
            }
        } else {
            if( hour == 0 ){
                formattedTime = String.format(Locale.getDefault() , "%02d:%02d AM " ,12 , minutes);
            }else {
                formattedTime = String.format(Locale.getDefault() , "%02d:%02d AM " ,hour , minutes);
            }
        }
        Log.d("time shit", formattedTime);
        return formattedTime;
    }

    public static String formattedDayForTask(int year, int month, int date){

        String formattedDate;

//        Task date
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(year, month, date);
        Date taskDay = new Date(calendarToday.getTimeInMillis());
        Log.d("date shit", taskDay.toString());

//        Today's date
        Date today = Calendar.getInstance().getTime();
        Log.d("Date shit", today.toString());

//        Tomorrow's date
        Calendar calendarTomo = Calendar.getInstance();
        calendarTomo.setTime(today);
        calendarTomo.add(Calendar.DATE, 1);
        Date tomorrow = calendarTomo.getTime();
        Log.d("date shit", tomorrow.toString());

//        Date formatter
        SimpleDateFormat monthDate = new SimpleDateFormat("MMM d", Locale.getDefault());
        SimpleDateFormat weekMonthDate = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
        if( today.compareTo(taskDay) == 0 ){
            formattedDate = "Today, " + monthDate.format(taskDay);
        }else if ( tomorrow.compareTo(taskDay) == 0 ){
            formattedDate = "Tomorrow, " + monthDate.format(taskDay);
        }else {
            formattedDate = weekMonthDate.format(taskDay);
        }

        Log.d("Date shit ", formattedDate);
        return formattedDate;

    }

}
