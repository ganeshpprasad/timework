package com.example.ganesh.timework.data;

/**
 * Created by Ganesh Prasad on 05-07-2016.
 */
public class RoutineItem {

    public static class Item{

        public final int id;

        public final String eventName;
        public final int timeHour;
        public final int timeMinutes;
        public String type;
        public boolean notify;

        boolean[] repeatDays;

        public boolean[] getRepeatDays() {
            return repeatDays;
        }

        public void setRepeatDays(boolean[] repeatDays) {
            this.repeatDays = repeatDays;
        }

        public Item(int _id , String _eventName , int _timeHour , int _timeMinutes , String _type , boolean _notify ){

            this.id = _id;

            eventName = _eventName;
            timeHour = _timeHour;
            timeMinutes = _timeMinutes;
            type = _type;
            notify = _notify;
        }

    }

}
