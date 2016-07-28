package com.example.ganesh.timework;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.DatabaseContract.NotesContract;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.Notes;

import java.util.Map;
import java.util.Set;

/**
 * Created by Ganesh Prasad on 19-07-2016.
 */
public class TestUtils extends AndroidTestCase {

    private static final String LOG_TAG = "Test Util Log";

    public static ContentValues getMockRoutineValues(){

        ContentValues contentValues = new ContentValues();

        contentValues.put( DatabaseContract.RoutineContract.COLUMN_ROUTINE_NAME , "Jogging" );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_ROUTINE_NOTIFY , Constants.RoutineBoolean.FALSE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_ROUTINE_TYPE , Constants.RoutineTypes.HOBBY );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_MON , Constants.RoutineBoolean.TRUE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_TUE , Constants.RoutineBoolean.TRUE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_WED , Constants.RoutineBoolean.TRUE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_THU , Constants.RoutineBoolean.TRUE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_FRI , Constants.RoutineBoolean.TRUE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_SAT , Constants.RoutineBoolean.TRUE );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_SUN , Constants.RoutineBoolean.TRUE );

        return contentValues;

    }

    public static ContentValues getMockRoutineValues( String name , int notify , String type ,
                                                      int mon , int tue , int wed , int thu , int fri , int sat , int sun) {

        Log.d( LOG_TAG , "values inserted" + name );

        ContentValues contentValues = new ContentValues();

        contentValues.put( DatabaseContract.RoutineContract.COLUMN_ROUTINE_NAME , name );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_ROUTINE_NOTIFY , notify );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_ROUTINE_TYPE , type );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_MON , mon );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_TUE , tue );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_WED , wed );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_THU , thu );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_FRI , fri );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_SAT , sat );
        contentValues.put( DatabaseContract.RoutineContract.COLUMN_DAY_SUN , sun );

        return contentValues;

    }

    public static void validateDbValues(Cursor valueCursor , ContentValues expectedValues ) {

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();

            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            Log.d( LOG_TAG + "/validate" , valueCursor.getString(idx) );
            assertEquals(expectedValue, valueCursor.getString(idx));
        }

    }

    public static ContentValues getMockNotesValues(){

        ContentValues contentValues = new ContentValues();

        Notes notes = new Notes( "note1" , Constants.RoutineTypes.HOBBY , "note to check things" , 5 , 6 , 8 , 10 );

        contentValues.put( NotesContract.COLUMN_NOTES_NAME , notes.getName() );
        contentValues.put( NotesContract.COLUMN_NOTES_TYPE , notes.getType() );
        contentValues.put( NotesContract.COLUMN_NOTES_CONTENT , notes.getContent() );
        contentValues.put( NotesContract.COLUMN_NOTES_CREATED_HOUR , notes.getCreatedHour() );
        contentValues.put( NotesContract.COLUMN_NOTES_CREATED_MINUTES , notes.getCreatedMinutes() );
        contentValues.put( NotesContract.COLUMN_NOTES_CREATED_DATE , notes.getCreatedDate() );
        contentValues.put( NotesContract.COLUMN_NOTES_CREATED_MONTH , notes.getCreatedMonth() );

        return contentValues;

    }

}
