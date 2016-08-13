package com.example.ganesh.timework;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.dialogs.DatePickerDialog;
import com.example.ganesh.timework.dialogs.TimePickerDialog;
import com.example.ganesh.timework.ui.TasksFragment;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.DescriptionTaskListenerModel;
import com.example.ganesh.timework.utils.Utilities;

public class TaskDescriptionActivity extends AppCompatActivity implements TimePickerDialog.SetTimeListener ,
 DatePickerDialog.setDateListener{

    private static final String LOG_TAG = "Task decs";

    boolean isEdit = false;
    int taskDbId;

    String taskName;
    String taskType;

    Spinner taskTypeSpinner;
    CheckBox taskNotifyCb;

    int mItemPosition;
    int taskHour;
    int taskMinutes;
    int taskDate;
    int taskMonth;
    boolean isNotify;
    boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

//        Toolbar implementation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tasks_detail_page);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        Initialise views
        final EditText taskNameTv = (EditText) findViewById(R.id.name_task_detail_page);
        TextView taskTimeTv = (TextView) findViewById(R.id.time_task_detail_page);
        TextView taskDateTv = (TextView) findViewById(R.id.date_task_detail_page);
        taskNotifyCb = (CheckBox) findViewById(R.id.notify_checkbox_task_detail_page);

//        Spinner initialise
        taskTypeSpinner = (Spinner) findViewById(R.id.type_task_detail_page);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.type_create_routine,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskTypeSpinner.setAdapter(arrayAdapter);

//        Get the item properties from intent
        Intent intent = getIntent();
        taskDbId = intent.getIntExtra(TasksFragment.TASK_DB_ID , -1);
        mItemPosition = intent.getIntExtra(TasksFragment.TASK_RECYCLERVIEW_POSITION , -1);

        if ( mItemPosition == -1 ){
            Log.d( LOG_TAG , "Invalid position" );
            finish();
        }

//        Database call
        Cursor cursor;
        if (taskDbId>0) {
            cursor = getContentResolver().query(DatabaseContract.TaskContract.buildUriWithId(taskDbId) ,
                    null , null , null , null);
        }else {
            cursor = null;
            Log.d( LOG_TAG , "id failed. " );
        }

        assert cursor != null;
        if ( cursor.moveToFirst() ){
            taskName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_NAME));
            taskType = cursor.getString( cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_TYPE) );
            taskDate = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_DATE));
            taskMonth = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_MONTH));
            taskHour = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_TIME_HOUR));
            taskMinutes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_TIME_MINUTES));
            isNotify = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TaskContract.COLUMN_TASK_NOTIFY)) == 1;
        }

        taskNameTv.setText(taskName);
        taskTypeSpinner.setSelection(Constants.getIntForTypeOfRoutine(taskType));

        String time = taskHour + ":" + taskMinutes;
        String date = taskDate + "/" + taskMonth;

        taskTimeTv.setText(time);
        taskDateTv.setText(date);
        taskNotifyCb.setChecked(isNotify);

        taskNameTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEdit = true;
                taskNameTv.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                taskName = s.toString();
            }
        });

        taskTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit = true;
                DialogFragment fragment = TimePickerDialog.newInstance(TaskDescriptionActivity.this);
                fragment.show(getSupportFragmentManager() , "Time fragment");
            }
        });

        taskDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit = true;
                DialogFragment fragment = DatePickerDialog.newInstance(TaskDescriptionActivity.this);
                fragment.show(getSupportFragmentManager() , "Date fragment");
            }
        });

    }

    private void updateTask(){
        int typeSelected = taskTypeSpinner.getSelectedItemPosition();
        String taskTypeStr = Constants.getTypeOfRoutine(typeSelected);
        boolean isNotify = taskNotifyCb.isChecked();

        ContentValues values;
        int updatedRows;

        if ( !taskName.isEmpty() ){
            values = new ContentValues();
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_NAME , taskName);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_TYPE , taskTypeStr);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_NOTIFY , isNotify);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_TIME_HOUR , taskHour);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_TIME_MINUTES , taskMinutes);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_DATE , taskDate);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_MONTH , taskMonth);
            updatedRows = getContentResolver().update(DatabaseContract.TaskContract.buildUriWithId(taskDbId) , values , null , null );
        }else{
            updatedRows = -1;
            isError = true;
        }

        if (updatedRows==1){
            setResult(mItemPosition);
        }

    }

    @Override
    public void onSetTime(int hour, int minutes) {
        taskHour = hour;
        taskMinutes = minutes;
    }

    @Override
    public void setDate(int year, int month, int day) {
        taskDate = day;
        taskMonth = month;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.activity_task_description_menu , menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return false;
        }

        if ( id == R.id.delete_tasks_detail_actionbar ){

            int deleterows = -1;
            boolean isPositive = Utilities.deleteItemRoutine(this);
            if ( isPositive ){
                Log.d(LOG_TAG , " Id " + taskDbId);
                deleterows = getContentResolver().delete(DatabaseContract.TaskContract.buildUriWithId(taskDbId) , null , null);
            }
            if (deleterows>0){
                DescriptionTaskListenerModel.getInstance().onDescriptionListener(mItemPosition);
                onBackPressed();
                return true;
            }else {
                Log.d(LOG_TAG , " " + deleterows);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public static final String ACTIVITY_TO_LANDING_PAGE = "fragment open";
    public static final int TASK_FRAGMENT = 2;

    @Override
    public void onBackPressed() {
        if (isEdit){
            updateTask();
        }
        if (!isError) {
            super.onBackPressed();
        }
    }
}
