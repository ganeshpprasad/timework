package com.example.ganesh.timework.utils;

import android.database.Cursor;

import com.example.ganesh.timework.data.DatabaseContract;

/**
 * Created by Ganesh Prasad on 15-01-2017.
 */

public class Routines {

    private int Routineid;
    private String RoutineName;
    private String RoutineType;
    private boolean[] DaysToRepeat = new boolean[7];
    private int hour;
    private int minutes;
    private boolean notify;

    public Routines(int routineid, String routineName, String routineType) {
        Routineid = routineid;
        RoutineName = routineName;
        RoutineType = routineType;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getRoutineid() {
        return Routineid;
    }

    public void setRoutineid(int routineid) {
        Routineid = routineid;
    }

    public String getRoutineName() {
        return RoutineName;
    }

    public boolean[] getDaysToRepeat() {
        return DaysToRepeat;
    }

    public void setRoutineName(String routineName) {
        RoutineName = routineName;
    }

    public String getRoutineType() {
        return RoutineType;
    }

    public void setRoutineType(String routineType) {
        RoutineType = routineType;
    }

    public void setDaysToRepeat(boolean[] daysToRepeat) {
        DaysToRepeat = daysToRepeat;
    }

    public static Routines getRoutinesFromCursor(Cursor cursor){
        Routines routines = null;

        if (cursor != null) {



                int id = cursor.getInt( cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract._ID) );
                String name = cursor.getString( cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_ROUTINE_NAME) );
                String type = cursor.getString( cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_ROUTINE_TYPE) );

                boolean[] daysToRepeat = new boolean[7];

                daysToRepeat[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_MON)) == 1;
                daysToRepeat[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_TUE)) == 1;
                daysToRepeat[2] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_WED)) == 1;
                daysToRepeat[3] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_THU)) == 1;
                daysToRepeat[4] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_FRI)) == 1;
                daysToRepeat[5] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_SAT)) == 1;
                daysToRepeat[6] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_DAY_SUN)) == 1;

                int hour = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_HOUR));
                int minutes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_MINUTES));

                routines = new Routines(id, name , type);
                routines.setDaysToRepeat(daysToRepeat);
                routines.setHour(hour);
                routines.setMinutes(minutes);



        }

        return routines;
    }

}
