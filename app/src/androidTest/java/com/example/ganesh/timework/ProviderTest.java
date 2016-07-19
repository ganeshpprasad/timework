package com.example.ganesh.timework;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;

import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.DatabaseHelper;
import com.example.ganesh.timework.data.DatabaseProvider;
import com.example.ganesh.timework.utils.Constants;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Ganesh Prasad on 18-07-2016.
 */
public class ProviderTest extends ProviderTestCase2<DatabaseProvider> {

    public ProviderTest() {
        super( DatabaseProvider.class , DatabaseContract.CONTENT_AUTHORITY);
    }

    @Before
    public void providerRegistryTest(){

        PackageManager pm = mContext.getPackageManager();

        ComponentName cn = new ComponentName( mContext.getPackageName() , DatabaseProvider.class.getName() );

        try {

            ProviderInfo pi = pm.getProviderInfo( cn , 0 );

            assertEquals( pi.authority , DatabaseContract.CONTENT_AUTHORITY );

        } catch (Exception e ) {
            e.printStackTrace();
        }

    }

    @Test
    public void typeTest(){

        long testIdRoutine = 1;
        Uri uriRoutine = DatabaseContract.RoutineContract.buildRoutineUri(testIdRoutine);

        String type = getMockContentResolver().getType(uriRoutine);
        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_ITEM_TYPE );

        uriRoutine = DatabaseContract.RoutineContract.buildRoutineUriWithDay(Constants.Days.MONDAY);
        type = getMockContentResolver().getType(uriRoutine);
        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_TYPE );

        uriRoutine = DatabaseContract.RoutineContract.buildRoutineUriWithType( Constants.RoutineTypes.PERSONAL ,
                Constants.Days.MONDAY );
        type = getMockContentResolver().getType(uriRoutine);
        assertEquals( type , DatabaseContract.RoutineContract.CONTENT_TYPE );

    }

    @Test
    public void insertTest() {

//        The Uri required for database from database contract - CONTENT_URI of the RoutineTable
        Uri uriRoutineInsert = DatabaseContract.RoutineContract.CONTENT_URI;
//        Content values from the testUtils
        ContentValues mockValues = TestUtils.getMockRoutineValues();

//        Insert called on mock content resolver
        Uri uriRoutineInsertResult = getMockContentResolver().insert( uriRoutineInsert ,  mockValues );
        assertTrue( uriRoutineInsertResult != null );
//        id for testing the read back from the database also testing insertion
        long id = ContentUris.parseId(uriRoutineInsert);
        assertTrue( id != -1 );

        Uri uriRoutineRead = DatabaseContract.RoutineContract.buildRoutineUri(id);
        Cursor cursor = getMockContentResolver().query( uriRoutineRead , null ,null , null , null );
        assertTrue( cursor != null );
        if ( cursor.moveToFirst() ) {
            TestUtils.validateDbValues( cursor , mockValues );
        }

    }



}
