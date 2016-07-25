package com.example.ganesh.timework.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract.RoutineContract;
import com.example.ganesh.timework.data.DatabaseContract.TaskContract;
import com.example.ganesh.timework.data.DatabaseContract.NotesContract;

/**
 * Created by Ganesh Prasad on 14-07-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

//    private static final String LOG_TAG = "DatabaseHelper Log";

    public static final String DATABASE_NAME = "timework.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String COMMA_SEP = ",";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

     final String SQL_CREATE_ROUTINE_TABLE =
            "CREATE TABLE " + RoutineContract.TABLE_NAME + " (" +
                    RoutineContract._ID + PRIMARY_KEY + COMMA_SEP +
                    RoutineContract.COLUMN_ROUTINE_NAME + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_ROUTINE_NOTIFY + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_ROUTINE_TYPE + TEXT_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_ROUTINE_TIME_HOUR + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_ROUTINE_TIME_MINUTES + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_MON + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_TUE + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_WED + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_THU + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_FRI + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_SAT + INTEGER_TYPE + COMMA_SEP +
                    RoutineContract.COLUMN_DAY_SUN + INTEGER_TYPE +
                    " );";

     final String SQL_CREATE_TASK_TABLE =
            "CREATE TABLE " + TaskContract.TABLE_NAME + " (" +
                    TaskContract._ID + PRIMARY_KEY + COMMA_SEP +
                    TaskContract.COLUMN_TASK_NAME + TEXT_TYPE + COMMA_SEP +
                    TaskContract.COLUMN_TASK_NOTIFY + INTEGER_TYPE + COMMA_SEP +
                    TaskContract.COLUMN_TASK_TYPE + TEXT_TYPE + COMMA_SEP +
                    TaskContract.COLUMN_TASK_DATE + INTEGER_TYPE + COMMA_SEP +
                    TaskContract.COLUMN_TASK_TIME + INTEGER_TYPE +
                    " );";

     final String SQL_CREATE_NOTE_TABLE =
            "CREATE TABLE " + NotesContract.TABLE_NAME + " (" +
                    NotesContract._ID + PRIMARY_KEY + COMMA_SEP +
                    NotesContract.COLUMN_NOTES_NAME + TEXT_TYPE + COMMA_SEP +
                    NotesContract.COLUMN_NOTES_CONTENT + TEXT_TYPE + COMMA_SEP +
                    NotesContract.COLUMN_NOTES_TYPE + TEXT_TYPE + COMMA_SEP +
                    NotesContract.COLUMN_NOTES_CREATED + INTEGER_TYPE +
                    " );";

    private static final String SQL_DELETE_ROUTINE_TABLE =
            "DROP TABLE IF EXISTS " + RoutineContract.TABLE_NAME;

    private static final String SQL_DELETE_TASK_TABLE =
            "DROP TABLE IF EXISTS " + TaskContract.TABLE_NAME;

    private static final String SQL_DELETE_NOTE_TABLE =
            "DROP TABLE IF EXISTS " + NotesContract.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTE_TABLE);
        db.execSQL(SQL_CREATE_ROUTINE_TABLE);
        db.execSQL(SQL_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_NOTE_TABLE);
        db.execSQL(SQL_DELETE_ROUTINE_TABLE);
        db.execSQL(SQL_DELETE_TASK_TABLE);
        onCreate(db);

    }
}
