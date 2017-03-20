package com.example.ganesh.timework.utils;

import android.database.Cursor;

import com.example.ganesh.timework.data.DatabaseContract.TaskContract;

/**
 * Created by Ganesh Prasad on 30-07-2016.
 */
public class Tasks {

  private int id;
  private String taskName;
  private String taskType;
  private boolean notify;

  private int hour;
  private int minutes;

  private int date;
  private int month;
  private int year;

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Tasks(String _taskName, String _taskType, boolean _notify) {

    this.taskName = _taskName;
    this.taskType = _taskType;
    this.notify = _notify;

  }

  public int getMonth() {
    return month;
  }

  public int getDate() {
    return date;
  }

  public int getMinutes() {

    return minutes;
  }

  public int getHour() {

    return hour;
  }

  public String getTaskName() {
    return taskName;
  }

  public String getTaskType() {
    return taskType;
  }

  public boolean isNotify() {
    return notify;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

//
//        method to read data from cursor into a Task item
//
  public static Tasks getTaskFromCursor(Cursor cursor) {

    Tasks tasks = null;

    if (cursor != null) {

      if (cursor.moveToNext()) {

        String taskName = cursor
            .getString(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_NAME));
        String taskType = cursor
            .getString(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_TYPE));

        int hour = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_TIME_HOUR));
        int minutes = cursor
            .getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_TIME_MINUTES));
        int date = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_DATE));
        int month = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_MONTH));
        int year = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_YEAR));

        boolean notify =
            cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_NOTIFY)) == 1;
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract._ID));

//                TODO do you need notify info here??
        tasks = new Tasks(taskName, taskType, notify);

        tasks.setId(id);

        tasks.setDate(date);
        tasks.setMonth(month);
        tasks.setYear(year);

        tasks.setHour(hour);
        tasks.setMinutes(minutes);

      }
    }
    return tasks;
  }

}
