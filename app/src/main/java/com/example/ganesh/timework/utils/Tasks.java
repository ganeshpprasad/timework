package com.example.ganesh.timework.utils;

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

    public Tasks( String _taskName , String _taskType , boolean _notify  ) {

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

}
