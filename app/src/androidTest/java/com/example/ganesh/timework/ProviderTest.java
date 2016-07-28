package com.example.ganesh.timework;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.example.ganesh.timework.data.DatabaseContract.RoutineContract;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.DatabaseHelper;
import com.example.ganesh.timework.data.DatabaseProvider;
import com.example.ganesh.timework.utils.Constants;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Ganesh Prasad on 18-07-2016.
 */
@RunWith(AndroidJUnit4.class)
public class ProviderTest extends InstrumentationTestCase {

//    public ProviderTest() {
//        super( DatabaseProvider.class , DatabaseContract.CONTENT_AUTHORITY);
//    }


    public ProviderTest() {
        super();
    }

    Context mContext;
    DatabaseHelper databaseHelper;
    ContentResolver contentResolver;
    SQLiteDatabase db;
    private static final String LOG_TAG = "Provider Test Log tag";

    @Before
    public void initialSetup(){

        mContext = InstrumentationRegistry.getContext();
        assertNotNull(mContext);

        contentResolver = mContext.getContentResolver();
        assertNotNull(contentResolver);

        injectInstrumentation( InstrumentationRegistry.getInstrumentation() );

        Log.d( LOG_TAG , "Setup method" );

        databaseHelper = new DatabaseHelper(getInstrumentation().getTargetContext());
        assertNotNull(databaseHelper);
        db = databaseHelper.getWritableDatabase();
        assertTrue( db.isOpen() );
        deleteRecords();

    }

    @Test
    public void getType(){
        Log.d( LOG_TAG , "Type test method" );

        long testIdRoutine = 1;
        Uri uriRoutine = DatabaseContract.NotesContract.buildNotesUriWithId(testIdRoutine);

        String type = contentResolver.getType(uriRoutine);
        assertEquals( type , DatabaseContract.NotesContract.CONTENT_ITEM_TYPE );

        Uri uriNotes = DatabaseContract.NotesContract.CONTENT_URI;
        String type2  = contentResolver.getType( uriNotes );
        assertEquals( type2 , DatabaseContract.NotesContract.CONTENT_TYPE);

//        uriRoutine = DatabaseContract.RoutineContract.buildRoutineUriWithDay(Constants.Days.MONDAY);
//        type = contentResolver.getType(uriRoutine);
//        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_TYPE );

//        uriRoutine = DatabaseContract.RoutineContract.buildRoutineUriWithType( Constants.RoutineTypes.PERSONAL ,
//                Constants.Days.MONDAY );
//        type = contentResolver.getType(uriRoutine);
//        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_TYPE );

    }

    @Test
    public void insert() {

        Log.d( LOG_TAG , "Insert test method" );

//        The Uri required for database from database contract - CONTENT_URI of the RoutineTable
        Uri uriNotesInsert = DatabaseContract.NotesContract.CONTENT_URI;
//        Content values from the testUtils
        ContentValues mockValues = TestUtils.getMockNotesValues();

//        Insert called on mock content resolver
        Uri uriRoutineInsertResult = contentResolver.insert( uriNotesInsert ,  mockValues );
        assertNotNull( uriRoutineInsertResult);

//        id for testing the read back from the database also testing insertion
        long id = ContentUris.parseId(uriRoutineInsertResult);
        assertTrue( id != -1 );

//        Uri uriRoutineRead = DatabaseContract.RoutineContract.buildRoutineUri(id);
        Cursor cursor = contentResolver.query( uriNotesInsert , null ,null , null , null );
        assertTrue( cursor != null );

        if ( cursor.moveToFirst() ) {
            TestUtils.validateDbValues( cursor , mockValues );
        }

        Log.d( LOG_TAG , "finished" );

    }

    public void queryForADay() {

        Log.v( LOG_TAG , "query for the day test" );

        Uri uriInsert = DatabaseContract.RoutineContract.CONTENT_URI;

        ContentValues record1 = TestUtils.getMockRoutineValues( "jog" , 1 ,
                Constants.RoutineTypes.HOBBY , 1 , 1 , 1 , 1 , 1 , 1 , 0);
        ContentValues record2 = TestUtils.getMockRoutineValues( "water" , 1 ,
                Constants.RoutineTypes.PERSONAL , 1 , 1 , 1 , 1 , 1 , 1 , 1);
        ContentValues record3 = TestUtils.getMockRoutineValues( "eat" , 0 ,
                Constants.RoutineTypes.PERSONAL , 1 , 1 , 1 , 0 , 1 , 1 , 0);
        ContentValues record4 = TestUtils.getMockRoutineValues( "work 1" , 1 ,
                Constants.RoutineTypes.WORK , 1 , 0 , 1 , 0 , 1 , 1 , 0);
        ContentValues record5 = TestUtils.getMockRoutineValues( " work 2" , 1 ,
                Constants.RoutineTypes.WORK , 1 , 1 , 1 , 1 , 0 , 0 , 0);
        ContentValues record6 = TestUtils.getMockRoutineValues( " work 3" , 1 ,
                Constants.RoutineTypes.WORK , 0 , 0 , 0 , 0 , 1 , 1 , 1);

        Uri insert1 = contentResolver.insert( uriInsert , record1 );
        long id1 = ContentUris.parseId( insert1 );
        assertTrue( id1 != -1 );
        Log.d( LOG_TAG , insert1.toString() );

        Uri insert2 = contentResolver.insert( uriInsert , record2 );
        long id2 = ContentUris.parseId( insert2 );
        assertTrue( id2 != -1 );
        Log.d( LOG_TAG , insert2.toString() );

        Uri insert3 = contentResolver.insert( uriInsert , record3 );
        long id3 = ContentUris.parseId( insert3 );
        assertTrue( id3 != -1 );
        Log.d( LOG_TAG , insert3.toString() );

        Uri insert4 = contentResolver.insert( uriInsert , record4 );
        long id4 = ContentUris.parseId( insert4 );
        assertTrue( id4 != -1 );
        Log.d( LOG_TAG , insert4.toString() );

        Uri insert5 = contentResolver.insert( uriInsert , record5 );
        long id5 = ContentUris.parseId( insert5 );
        assertTrue( id5 != -1 );
        Log.d( LOG_TAG , insert5.toString() );

        Uri insert6 = contentResolver.insert( uriInsert , record6 );
        long id6 = ContentUris.parseId( insert6 );
        assertTrue( id6 != -1 );
        Log.d( LOG_TAG , insert6.toString() );

        Uri uriRoutineQueryForType = RoutineContract.buildRoutineUriWithType( Constants.RoutineTypes.PERSONAL ,
                Constants.Days.MONDAY );

        Log.d( LOG_TAG , uriRoutineQueryForType.toString() );

        Cursor cursor = contentResolver.query( uriRoutineQueryForType  , null , null , null , null );
        if ( cursor.moveToFirst() ) {

            do {

                String name = cursor.getString( cursor.getColumnIndexOrThrow( RoutineContract.COLUMN_ROUTINE_NAME ) );
                Log.d( LOG_TAG , name );
                String type = cursor.getString( cursor.getColumnIndexOrThrow( RoutineContract.COLUMN_ROUTINE_TYPE ) );
                Log.d( LOG_TAG , type );

            }while ( cursor.moveToNext() );

        }

    }

//    Method to delete all the previous records at the beginning. Called at Before class
    public void deleteRecords(){

        Log.d( LOG_TAG , "Delete Records method" );

        long deleteId = db.delete( DatabaseContract.NotesContract.TABLE_NAME , null , null );
        assertTrue( deleteId != -1 );

        Cursor cursor = db.query( DatabaseContract.NotesContract.TABLE_NAME , null , null , null ,null ,null ,null );
        assertEquals( cursor.getCount() , 0 );
        cursor.close();

    }

}
