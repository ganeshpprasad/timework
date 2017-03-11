package com.example.ganesh.timework.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract.RoutineContract;

import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.NotifyRoutineOrTask;

/**
 * Created by Ganesh Prasad on 22-07-2016.
 *
 */
public class DialogToDatabaseAdapter {

//    private static final String LOG_TAG = "Database adapter";

    private String routineName;
    private int dayGroup;
    private int typeSelected;
    private int timeSize;
    private Boolean notify;

    private int hour;
    private int minutes;

    private boolean[] custom = null;
    private boolean isUpdate;
    private Context context;

    private int id;


    public DialogToDatabaseAdapter(Context _context , String _routineName , int _dayGroup , int _typeSelected ,
                                   Boolean _notify , boolean[] _custom , int _hour , int _minutes , boolean _isUpdate, int routineTimeSize ) {
        this.routineName = _routineName;
        this.dayGroup = _dayGroup;
        this.typeSelected = _typeSelected;
        this.timeSize = routineTimeSize;
        this.notify = _notify;

        this.hour = _hour;
        this.minutes = _minutes;

        this.context = _context;
        this.custom = _custom;
        this.isUpdate = _isUpdate;
    }

    public boolean addValuesToDb(){

        String LOG_TEXT = "Frag to database";

//        getting an array of integer based on the days group
        int[] daysArray = Constants.getDaysArray( dayGroup , custom );

//        storing the int value in database.
        int notifyInt = Constants.booleanToInt( notify );

//        getting the type string based on typeSelected
        String typeString = Constants.getTypeOfRoutine( typeSelected );
//
        String timeSizeString = Constants.getTimeSize( timeSize );

//        Get an array of days column names for for loop
        String[] daysColumnArray = RoutineContract.getColumnNameDaysArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put( RoutineContract.COLUMN_ROUTINE_NAME , routineName );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TYPE , typeString );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TIME_SIZE , timeSizeString );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_NOTIFY , notifyInt );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TIME_HOUR , hour );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TIME_MINUTES , minutes );

        for ( int i = 0; i < daysArray.length; i++ ) {
            contentValues.put( daysColumnArray[i] , daysArray[i] );
        }

        int dbId;

        if (isUpdate){
//            Update an existing routine
            dbId = id;
            int rows = context.getContentResolver().update( RoutineContract.buildRoutineUri(id) , contentValues ,null,null );
            if(rows != 0){
                Log.d(LOG_TEXT, " Row number : " + rows);
            }
        } else {
            Uri insertUri = context.getContentResolver().insert(RoutineContract.CONTENT_URI, contentValues);
            dbId = (int) ContentUris.parseId(insertUri);
            if(dbId != 0){
                Log.d(LOG_TEXT, " Id number : " + dbId);
            }
        }

        if( notify  ){
            // notify is true
            // Call the notification alarm setter
            Log.d(LOG_TEXT, " Database ID : " + dbId);
            NotifyRoutineOrTask.setRoutineNotificationAlarm(context, dbId);
        } else {
            NotifyRoutineOrTask.removeRoutineNotification(context, dbId);
        }

        return true;
    }

    public void setId(int id) {
        this.id = id;
    }
}
