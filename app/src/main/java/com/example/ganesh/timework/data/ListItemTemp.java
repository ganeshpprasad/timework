package com.example.ganesh.timework.data;

/**
 * Created by Ganesh Prasad on 05-07-2016.
 */
public class ListItemTemp {

    public static class Item{

        public final int id;
        public final String eventName;
        public final String time;
        public enum type { PERSONAL , WORK , HOBBIES }
        public enum repeat { WEEKDAYS , ALLDAYS , NONE }

        public final repeat r;
        public final type t;

        public Item( int _id , String _eventName , String _time , type _type , repeat _r ){
            id = _id;
            eventName = _eventName;
            time = _time;
            t = _type;
            r = _r;
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
