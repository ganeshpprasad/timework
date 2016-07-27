package com.example.ganesh.timework.utils;

import android.util.Log;

/**
 * Created by Ganesh Prasad on 15-07-2016.
 */
public class Constants {

    public static final class Days {
        public static final int MONDAY = 1;
        public static final int TUESDAY = 2;
        public static final int WEDNESDAY = 3;
        public static final int THURSDAY = 4;
        public static final int FRIDAY = 5;
        public static final int SATURDAY = 6;
        public static final int SUNDAY = 7;
    }

    public static final class RoutineTypes {

        public static final String PERSONAL = "personal";
        public static final String WORK = "work";
        public static final String HOBBY = "hobby";

    }

    public static final class RoutineBoolean {

        public static final int TRUE = 1;
        public static final int FALSE = 0;

    }

    public static final class DayGroups {

        public static final int WEEKDAYS = 1;
        public static final int ALLDAYS = 2;
        public static final int CUSTOM = 3;

    }

    public static int[] getDaysArray( int days , boolean[] custom ){

        int[] array;

        if ( days == DayGroups.WEEKDAYS ) {
            array = new int[] { 1 , 1 , 1 , 1 , 1 , 0 , 0 };
        }else if ( days == DayGroups.ALLDAYS ) {
            array = new int[] { 1 , 1 , 1 , 1 , 1 , 1 , 1 };
        }else {
            array = new int[7];
            for ( int i = 0; i < custom.length; i++ ) {
                array[i] = Constants.booleanToInt( custom[i] );
            }
        }
        return array;
    }

    public static String getTypeOfRoutine( int typeSelected ) {

        switch ( typeSelected ) {

            case 0 : return RoutineTypes.HOBBY;
            case 1 : return  RoutineTypes.PERSONAL;
            case 2 : return  RoutineTypes.WORK;
            default: return null;
        }
    }

    public static int booleanToInt( boolean bool ) {

        if ( bool ) return 1;
        else return 0;

    }

}
