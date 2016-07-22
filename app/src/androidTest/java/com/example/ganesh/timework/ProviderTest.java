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


//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//        initialSetup();
//    }

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

        PackageManager pm = mContext.getPackageManager();
        ComponentName cn = new ComponentName( mContext.getPackageName() , DatabaseProvider.class.getName() );

        try {
            ProviderInfo pi = pm.getProviderInfo( cn , 0 );
            Log.d( LOG_TAG , pi.authority );
            assertEquals( pi.authority , DatabaseContract.CONTENT_AUTHORITY );
        } catch (Exception e ) {
            e.printStackTrace();
        }

    }

    public void getType(){

//        deleteRecords();

        Log.d( LOG_TAG , "Type test method" );

        long testIdRoutine = 1;
        Uri uriRoutine = DatabaseContract.RoutineContract.buildRoutineUri(testIdRoutine);

        String type = contentResolver.getType(uriRoutine);
        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_ITEM_TYPE );

        uriRoutine = DatabaseContract.RoutineContract.buildRoutineUriWithDay(Constants.Days.MONDAY);
        type = contentResolver.getType(uriRoutine);
        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_TYPE );

        uriRoutine = DatabaseContract.RoutineContract.buildRoutineUriWithType( Constants.RoutineTypes.PERSONAL ,
                Constants.Days.MONDAY );
        type = contentResolver.getType(uriRoutine);
        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_TYPE );

    }

    public void insert() {

        Log.d( LOG_TAG , "Insert test method" );

//        The Uri required for database from database contract - CONTENT_URI of the RoutineTable
        Uri uriRoutineInsert = DatabaseContract.RoutineContract.CONTENT_URI;
//        Content values from the testUtils
        ContentValues mockValues = TestUtils.getMockRoutineValues();

//        Insert called on mock content resolver
        Uri uriRoutineInsertResult = contentResolver.insert( uriRoutineInsert ,  mockValues );
        assertNotNull( uriRoutineInsertResult);

//        id for testing the read back from the database also testing insertion
        long id = ContentUris.parseId(uriRoutineInsertResult);
        assertTrue( id != -1 );

//        Uri uriRoutineRead = DatabaseContract.RoutineContract.buildRoutineUri(id);
        Cursor cursor = contentResolver.query( uriRoutineInsert , null ,null , null , null );
        assertTrue( cursor != null );
        if ( cursor.moveToFirst() ) {
            TestUtils.validateDbValues( cursor , mockValues );
        }

        Log.d( LOG_TAG , "finished" );

    }

    @Test
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

        Cursor cursor1 = contentResolver.query( DatabaseContract.RoutineContract.buildRoutineUriWithDay( Constants.Days.FRIDAY ) ,
                null , null , null , null);
        assertNotNull( cursor1 );
        if ( cursor1.moveToFirst() ) {

            do {

                String name = cursor1.getString( cursor1.getColumnIndexOrThrow( DatabaseContract.RoutineContract.COLUMN_ROUTINE_NAME ) );
                Log.d( LOG_TAG , RoutineContract.COLUMN_ROUTINE_NAME + name );
                String type = cursor1.getString( cursor1.getColumnIndexOrThrow( RoutineContract.COLUMN_ROUTINE_TYPE ) );
                Log.d( LOG_TAG , RoutineContract.COLUMN_ROUTINE_TYPE + type );
                int notify = cursor1.getInt( cursor1.getColumnIndexOrThrow( RoutineContract.COLUMN_ROUTINE_NOTIFY ) );
                Log.d( LOG_TAG , RoutineContract.COLUMN_ROUTINE_NOTIFY + " " + notify );

            }while ( cursor1.moveToNext() );

        }

    }

//    Method to delete all the previous records at the beginning. Called at Before class
    public void deleteRecords(){

        Log.d( LOG_TAG , "Delete Records method" );

        long deleteId = db.delete( DatabaseContract.RoutineContract.TABLE_NAME , null , null );
        assertTrue( deleteId != -1 );

        Cursor cursor = db.query( DatabaseContract.RoutineContract.TABLE_NAME , null , null , null ,null ,null ,null );
        assertEquals( cursor.getCount() , 0 );
        cursor.close();

    }

}
