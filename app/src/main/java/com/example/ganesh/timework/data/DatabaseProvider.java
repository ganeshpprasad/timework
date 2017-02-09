package com.example.ganesh.timework.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.util.Log;

//import com.example.ganesh.timework.data.DatabaseContract.NotesContract;

/**
 * Created by Ganesh Prasad on 15-07-2016.
 */
public class DatabaseProvider extends ContentProvider {

    private static final String LOG_TAG = "Data Provider";

    private static final int ROUTINE_TABLE = 101;
    private static final int ROUTINE_TABLE_ID = 102;
    private static final int ROUTINE_TABLE_WITH_TYPE = 103;
    private static final int ROUTINE_TABLE_WITH_DAY = 104;

//    private static final int NOTES_TABLE = 201;
//    private static final int NOTES_TABLE_ID = 202;
//    private static final int NOTES_TABLE_WITH_TYPE = 203;
//    private static final int NOTES_TABLE_WITH_CREATED_DATE = 204;

    private static final int TASKS_TABLE = 301;
    private static final int TASKS_WITH_ID = 302;
    private static final int TASKS_WITH_TYPE = 303;

    private static final UriMatcher sUriMatcher = getUriMatcher();
    public DatabaseHelper mOpenDbHelper;

    private static SQLiteQueryBuilder sRoutineQueryBuilder;
//    private static SQLiteQueryBuilder sNotesQueryBuilder;
    private static SQLiteQueryBuilder sTasksQueryBuilder;

    static {
        sRoutineQueryBuilder = new SQLiteQueryBuilder();
        sRoutineQueryBuilder.setTables(DatabaseContract.RoutineContract.TABLE_NAME);

//        sNotesQueryBuilder = new SQLiteQueryBuilder();
//        sNotesQueryBuilder.setTables( DatabaseContract.NotesContract.TABLE_NAME );

        sTasksQueryBuilder = new SQLiteQueryBuilder();
        sTasksQueryBuilder.setTables(DatabaseContract.TaskContract.TABLE_NAME);
    }

    private static UriMatcher getUriMatcher() {

        /**
         * UriMatcher part of the Routine table
         * Here all the tables share the same UriMatcher
         */
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

//        Entire table
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_ROUTINE , ROUTINE_TABLE);
//        specific row by the id - /id
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_ROUTINE + "/#" , ROUTINE_TABLE_ID);
//        set of rows based on day
//        used in routine day pages - /day
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_ROUTINE + "/*" , ROUTINE_TABLE_WITH_DAY);
//        set of rows returned based on the type
//        used in routine day page filter button - /day/type
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_ROUTINE + "/*/*" , ROUTINE_TABLE_WITH_TYPE);

//        uriMatcher.addURI( DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_NOTE , NOTES_TABLE);
//
//        uriMatcher.addURI( DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_NOTE + "/#" , NOTES_TABLE_ID );
//
//        uriMatcher.addURI( DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_NOTE + "/*" , NOTES_TABLE_WITH_TYPE );

//        task table constants
        uriMatcher.addURI( DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_TASK , TASKS_TABLE );

        uriMatcher.addURI( DatabaseContract.CONTENT_AUTHORITY , DatabaseContract.PATH_TASK + "/#" , TASKS_WITH_ID );

        return uriMatcher;
    }

//    Method to retuen a row based on the id in the uri
    private Cursor getRoutineWithId(Uri uri , String[] projection , String sortOrder) {

        String stringId = DatabaseContract.RoutineContract.getIdFromUri(uri);

        String selection = DatabaseContract.RoutineContract.TABLE_NAME + "." +
                DatabaseContract.RoutineContract._ID + " = ? ";
        String[] selectionArgs = new String[]{stringId};

        return sRoutineQueryBuilder.query(
                mOpenDbHelper.getReadableDatabase() ,
                projection ,
                selection ,
                selectionArgs ,
                null ,
                null ,
                sortOrder
        );

    }

//    method to return cursor for rows belonging to a particular day
    private Cursor getRoutineWithDay( Uri uri , String[] projection , String sortOrder ) {

        String day = DatabaseContract.RoutineContract.getColumnDayFromUri(uri);

        String selection = DatabaseContract.RoutineContract.TABLE_NAME + "." +
                day + " = ? ";
        String[] selectionArgs = new String[]{"1"};

        return sRoutineQueryBuilder.query(
                mOpenDbHelper.getReadableDatabase() ,
                projection ,
                selection ,
                selectionArgs ,
                null ,
                null ,
                sortOrder
        );
    }

//    Method to retuen the filtered rows of the day based on the type in the uri
    private Cursor getRoutineWithType( Uri uri , String[] projection , String sortOrder ) {

        String day = DatabaseContract.RoutineContract.getColumnDayFromUri(uri);
        String type = DatabaseContract.RoutineContract.getRoutineType(uri);

        Log.d( LOG_TAG , day + " " + type );

        String selection = day + " = ? AND " + DatabaseContract.RoutineContract.COLUMN_ROUTINE_TYPE +
                " = ? ";
        String[] selectionArgs = new String[] {"1" , type};

        return sRoutineQueryBuilder.query(
                mOpenDbHelper.getReadableDatabase() ,
                projection ,
                selection ,
                selectionArgs ,
                null ,
                null ,
                sortOrder
        );

    }

//    Method to return the cursor for Notes on a id from uri
//    private Cursor getNotesWithId( Uri uri , String[] projection , String sortOrder ) {
//
//        String id = NotesContract.getIdFromUri(uri);
//
//        String selection = NotesContract._ID + " = ?";
//        String[] selectionArgs = new String[]{id};
//
//        return sNotesQueryBuilder.query(
//                mOpenDbHelper.getReadableDatabase() ,
//                projection ,
//                selection ,
//                selectionArgs ,
//                null ,
//                null ,
//                sortOrder
//        );
//
//    }

//    method to return the cursor for tasks on a id from uri
    private Cursor getTasksWithId( Uri uri , String[] projection , String sortOrder ) {

        String id = DatabaseContract.TaskContract.getIdFromUri( uri );

        String selection = DatabaseContract.TaskContract._ID + " = ?";
        String[] selectionArgs = new String[] {id};

        return sTasksQueryBuilder.query(
                mOpenDbHelper.getReadableDatabase() ,
                projection ,
                selection ,
                selectionArgs ,
                null ,
                null ,
                sortOrder
        );

    }

    @Override
    public boolean onCreate() {
        mOpenDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case ROUTINE_TABLE : {
                retCursor = mOpenDbHelper.getReadableDatabase().query(
                        DatabaseContract.RoutineContract.TABLE_NAME ,
                        projection ,
                        selection ,
                        selectionArgs ,
                        null ,
                        null ,
                        sortOrder
                );
                return retCursor;
            }

            case ROUTINE_TABLE_ID : {
                retCursor = getRoutineWithId(uri , projection , sortOrder);
                return retCursor;
            }

            case ROUTINE_TABLE_WITH_DAY : {
                retCursor = getRoutineWithDay( uri , projection , sortOrder );
                return retCursor;
            }

            case ROUTINE_TABLE_WITH_TYPE : {
                retCursor = getRoutineWithType( uri , projection , sortOrder);
                return retCursor;
            }

//            case NOTES_TABLE : {
//
//                retCursor = mOpenDbHelper.getReadableDatabase().query(
//                        NotesContract.TABLE_NAME ,
//                        projection ,
//                        selection ,
//                        selectionArgs ,
//                        null ,
//                        null ,
//                        sortOrder
//                );
//                return retCursor;
//
//            }

//            case NOTES_TABLE_ID : {
//                retCursor = getNotesWithId(uri , projection , sortOrder);
//                return  retCursor;
//            }

            case TASKS_TABLE : {

                retCursor = mOpenDbHelper.getReadableDatabase().query(
                        DatabaseContract.TaskContract.TABLE_NAME ,
                        projection ,
                        selection ,
                        selectionArgs ,
                        null ,
                        null ,
                        sortOrder
                );
                return retCursor;

            }

            case TASKS_WITH_ID : {
                retCursor = getTasksWithId( uri , projection , sortOrder );
                return retCursor;
            }

        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)){
//            Return types for the routine table
//            query returns entire table
            case ROUTINE_TABLE : return DatabaseContract.RoutineContract.CONTENT_TYPE;
//            query returns the item specified by the id
            case ROUTINE_TABLE_ID : return DatabaseContract.RoutineContract.CONTENT_ITEM_TYPE;
//            query to return the set of items matching the type and day
            case ROUTINE_TABLE_WITH_DAY : return DatabaseContract.RoutineContract.CONTENT_TYPE;
            case ROUTINE_TABLE_WITH_TYPE : return DatabaseContract.RoutineContract.CONTENT_TYPE;

//            retuen calls for Notes Table
//            case NOTES_TABLE : return NotesContract.CONTENT_TYPE;
//
//            case NOTES_TABLE_ID : return NotesContract.CONTENT_ITEM_TYPE;
//
//            case NOTES_TABLE_WITH_TYPE : return NotesContract.CONTENT_TYPE;

//            return types for tasks table
            case TASKS_TABLE : return DatabaseContract.TaskContract.CONTENT_TYPE;

            case TASKS_WITH_ID : return DatabaseContract.TaskContract.CONTENT_ITEM_TYPE;

        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {

            case ROUTINE_TABLE : {
                long id = mOpenDbHelper.getWritableDatabase().insert(
                        DatabaseContract.RoutineContract.TABLE_NAME ,
                        null ,
                        values
                        );
                if ( id > 0 ) {
                    returnUri = DatabaseContract.RoutineContract.buildRoutineUri( id );
                    return returnUri;
                } else {
                    return null;
                }
            }

//            case NOTES_TABLE : {
//
//                long id = mOpenDbHelper.getWritableDatabase().insert(
//                        NotesContract.TABLE_NAME ,
//                        null ,
//                        values
//                );
//                if ( id > 0 ) {
//                    returnUri = NotesContract.buildNotesUriWithId( id );
//                    return returnUri;
//                } else{
//                    return null;
//                }
//
//            }

            case TASKS_TABLE : {

                long id = mOpenDbHelper.getWritableDatabase().insert(
                        DatabaseContract.TaskContract.TABLE_NAME ,
                        null ,
                        values
                );
                if ( id > 0 ) {
                    returnUri = DatabaseContract.TaskContract.buildUriWithId(id);
                    return returnUri;
                }else{
                    return null;
                }

            }

            default: return null;

        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int match = sUriMatcher.match(uri);

        switch ( match ) {

//            case NOTES_TABLE_ID : {
//                return mOpenDbHelper.getWritableDatabase().delete(
//                  NotesContract.TABLE_NAME ,
//                  NotesContract._ID + " = ?" ,
//                  new String[] { NotesContract.getIdFromUri(uri) }
//                );
//            }

            case ROUTINE_TABLE_ID : {
                return mOpenDbHelper.getWritableDatabase().delete(
                        DatabaseContract.RoutineContract.TABLE_NAME ,
                        DatabaseContract.RoutineContract._ID + " = ?" ,
                        new String[] { DatabaseContract.RoutineContract.getIdFromUri(uri) }
                );
            }

            case TASKS_WITH_ID : {
                Log.d(LOG_TAG , DatabaseContract.TaskContract.getIdFromUri(uri));

                return mOpenDbHelper.getWritableDatabase().delete(
                        DatabaseContract.TaskContract.TABLE_NAME ,
                        DatabaseContract.TaskContract._ID + " = ?" ,
                        new String[] { DatabaseContract.TaskContract.getIdFromUri(uri) }
                );
            }

        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int match = sUriMatcher.match(uri);

        switch (match) {

//            case NOTES_TABLE_ID : {
//                return mOpenDbHelper.getWritableDatabase().update(
//                        NotesContract.TABLE_NAME ,
//                        values ,
//                        NotesContract._ID + " = ?" ,
//                        new String[] {NotesContract.getIdFromUri(uri)}
//                );
//            }

            case ROUTINE_TABLE_ID : {
                return mOpenDbHelper.getWritableDatabase().update(
                        DatabaseContract.RoutineContract.TABLE_NAME ,
                        values ,
                        DatabaseContract.RoutineContract._ID + " = ?" ,
                        new String[] {DatabaseContract.RoutineContract.getIdFromUri(uri)}
                );
            }

            case TASKS_WITH_ID : {
                return mOpenDbHelper.getWritableDatabase().update(
                        DatabaseContract.TaskContract.TABLE_NAME ,
                        values ,
                        DatabaseContract.TaskContract._ID + " = ?" ,
                        new String[] {DatabaseContract.TaskContract.getIdFromUri(uri)}
                );
            }

        }

        return 0;

    }
}
