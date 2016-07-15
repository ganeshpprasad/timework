package com.example.ganesh.timework.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.ganesh.timework.utils.Constants.Days;

import java.nio.channels.UnsupportedAddressTypeException;

/**
 * Created by Ganesh Prasad on 14-07-2016.
 */
public final class DatabaseContract {

    //    Content uri for content providers
    public final static String CONTENT_AUTHORITY = "com.example.ganesh.timework.app";

    public final static Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ROUTINE = "routine";
    private static final String PATH_TASK = "task";
    private static final String PATH_NOTE = "note";

    //    Empty constructor
    public DatabaseContract() {
    }

    public static abstract class RoutineContract implements BaseColumns {

        //        Content Uri part of the contract
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_ROUTINE).build();

        //        Content types for the uri matcher
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTINE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTINE;

        public static final String TABLE_NAME = "routine";

        public static final String COLUMN_ROUTINE_NAME = "name";
        public static final String COLUMN_ROUTINE_NOTIFY = "notify";
        public static final String COLUMN_ROUTINE_TYPE = "type";
        public static final String COLUMN_DAY_MON = "mon";
        public static final String COLUMN_DAY_TUE = "tue";
        public static final String COLUMN_DAY_WED = "wed";
        public static final String COLUMN_DAY_THU = "thu";
        public static final String COLUMN_DAY_FRI = "fri";
        public static final String COLUMN_DAY_SAT = "sat";
        public static final String COLUMN_DAY_SUN = "sun";

//        Helper methods to return uris
        public static Uri buildRoutineUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI , id);
        }

        public static Uri buildRoutineUriWithDay( int day ) {
            switch (day) {
                case Days.MONDAY : return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_MON).build();
                case Days.TUESDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_TUE).build();
                case Days.WEDNESDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_WED).build();
                case Days.THURSDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_THU).build();
                case Days.FRIDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_FRI).build();
                case Days.SATURDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_SAT).build();
                case Days.SUNDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_SUN).build();
                default: return null;
            }
        }

        public static Uri buildRoutineUriWithType( String type , int day ) {
            switch (day) {
                case Days.MONDAY : return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_MON).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                case Days.TUESDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_TUE).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                case Days.WEDNESDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_WED).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                case Days.THURSDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_THU).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                case Days.FRIDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_FRI).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                case Days.SATURDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_SAT).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                case Days.SUNDAY: return CONTENT_URI.buildUpon().appendPath(COLUMN_DAY_SUN).appendQueryParameter(COLUMN_ROUTINE_TYPE ,
                        type).build();
                default: return null;
            }
        }

        public static String getColumnDayFromUri(Uri uri) {
            return uri.getPathSegments().get(1) ;
        }

        public static String getRoutineType( Uri uri ) {
            return uri.getPathSegments().get(2);
        }

    }

    public static abstract class TaskContract implements BaseColumns {

        //        Content uri for the content provider
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_TASK).build();

        //        Content types for the uri matcher
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;


        public static final String TABLE_NAME = "task";

        public static final String COLUMN_TASK_NAME = "name";
        public static final String COLUMN_TASK_NOTIFY = "notify";
        public static final String COLUMN_TASK_TYPE = "type";
        public static final String COLUMN_TASK_DATE = "date";
        public static final String COLUMN_TASK_TIME = "time";

    }

    public static abstract class NotesContract implements BaseColumns {

        //        Content uri for the content provider
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_NOTE).build();

        //         Content types for the uri matcher
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTE;

        public static final String TABLE_NAME = "note";

        public static final String COLUMN_NOTES_NAME = "name";
        public static final String COLUMN_NOTES_CONTENT = "content";
        public static final String COLUMN_NOTES_TYPE = "type";
        public static final String COLUMN_NOTES_CREATED = "created";

    }

}
