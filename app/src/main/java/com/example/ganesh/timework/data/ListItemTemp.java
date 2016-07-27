package com.example.ganesh.timework.data;

/**
 * Created by Ganesh Prasad on 05-07-2016.
 */
public class ListItemTemp {

    public static class Item{

        public final String eventName;
        public final int timeHour;
        public final int timeMinutes;
        public String type;
        public boolean repeat;

        public Item(String _eventName , int _timeHour , int _timeMinutes , String _type , boolean _r ){
            eventName = _eventName;
            timeHour = _timeHour;
            timeMinutes = _timeMinutes;
            type = _type;
            repeat = _r;
        }

    }

    public static class TaskItem{

        public final int id;
        public final String taskName;
        public final String taskTime;
        public final String taskDate;
        public enum type { PERSONAL , WORK , HOBBIES }
        public final boolean reminder;

        public final type taskType;

        public TaskItem ( int _id , String _taskName , String _taskTime ,String _taskDate , type _type , boolean _reminder ){
            id = _id;
            taskName = _taskName;
            taskDate = _taskDate;
            taskTime = _taskTime;
            taskType = _type;
            reminder = _reminder;
        }

    }

}
