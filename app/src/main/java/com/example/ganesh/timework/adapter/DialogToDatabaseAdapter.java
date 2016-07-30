package com.example.ganesh.timework.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.DatabaseContract.RoutineContract;

import com.example.ganesh.timework.ui.WeekdayFragment;
import com.example.ganesh.timework.utils.Constants;

/**
 * Created by Ganesh Prasad on 22-07-2016.
 *
 */
public class DialogToDatabaseAdapter {

    private static final String LOG_TAG = "Database adapter";

    String routineName;
    int dayGroup;
    int typeSelected;
    Boolean notify;

    int hour;
    int minutes;

    boolean[] custom = null;

    Context context;

    public DialogToDatabaseAdapter(Context _context , String _routineName , int _dayGroup , int _typeSelected , Boolean _notify , int _hour , int _minutes ) {

        this.routineName = _routineName;
        this.dayGroup = _dayGroup;
        this.typeSelected = _typeSelected;
        this.notify = _notify;

        this.hour = _hour;
        this.minutes = _minutes;

        this.context = _context;
    }

    public DialogToDatabaseAdapter( Context _context , String _routineName , int _dayGroup , int _typeSelected , Boolean _notify , boolean[] _custom , int _hour , int _minutes) {

        this( _context , _routineName , _dayGroup ,_typeSelected , _notify , _hour , _minutes );

        this.custom = _custom;

    }

    int notifyInt;
    int[] daysArray;
    String typeString;

    ContentValues contentValues;

    public boolean addValuesToDb(){

//        getting an array of integer based on the days group
        daysArray = Constants.getDaysArray( dayGroup , custom );

//        storing the int value in database.
        notifyInt = Constants.booleanToInt( notify );

//        getting the type string based on typeSelected
        typeString = Constants.getTypeOfRoutine( typeSelected );

//        Get an array of days column names for for loop
        String[] daysColumnArray = RoutineContract.getColumnNameDaysArray();

        contentValues = new ContentValues();

        contentValues.put( RoutineContract.COLUMN_ROUTINE_NAME , routineName );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TYPE , typeString );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_NOTIFY , notifyInt );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TIME_HOUR , hour );
        contentValues.put( RoutineContract.COLUMN_ROUTINE_TIME_MINUTES , minutes );

        for ( int i = 0; i < daysArray.length; i++ ) {
            contentValues.put( daysColumnArray[i] , daysArray[i] );
        }

        Uri insertUri = context.getContentResolver().insert(RoutineContract.CONTENT_URI , contentValues );
        long id = ContentUris.parseId( insertUri );

        return true;
    }



}
