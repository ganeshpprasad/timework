package com.example.ganesh.timework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.DatabaseHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Ganesh Prasad on 16-07-2016.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest extends InstrumentationTestCase {

    private static final String LOG_TAG = "Database testing";

    DatabaseHelper mOpenHelper;
    Context mContext;

    @Before
    public void setUp() throws Exception{
        mContext = InstrumentationRegistry.getTargetContext();
        assertTrue( mContext != null );
        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    @Test
    public void CreateTables() throws Throwable {

        mOpenHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        assertEquals(true , db.isOpen());

//        ContentValues testValues = TestUtils.getMockRoutineValues();
        ContentValues testValues = TestUtils.getMockNotesValues();

        long insertionId = db.insert( DatabaseContract.NotesContract.TABLE_NAME , null , testValues  );
        assertTrue(insertionId != -1);

        Cursor cursor = db.query(DatabaseContract.NotesContract.TABLE_NAME ,
                null ,
                null ,
                null ,
                null ,
                null ,
                null );

        if (cursor.moveToFirst()) {

            TestUtils.validateDbValues( cursor , testValues );

        }

        cursor.close();
        db.close();

    }

}
