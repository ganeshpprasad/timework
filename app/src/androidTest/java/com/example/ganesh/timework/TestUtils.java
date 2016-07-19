package com.example.ganesh.timework;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.utils.Constants;

import java.util.Map;
import java.util.Set;

/**
 * Created by Ganesh Prasad on 19-07-2016.
 */
public class TestUtils extends AndroidTestCase {

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

    public static void validateDbValues(Cursor valueCursor , ContentValues expectedValues ) {

        String error = " Routine query validation ";

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();

            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();

            assertEquals(expectedValue, valueCursor.getString(idx));
        }

    }

}
