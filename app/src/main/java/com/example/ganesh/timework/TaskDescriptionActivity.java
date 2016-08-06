package com.example.ganesh.timework;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

public class TaskDescriptionActivity extends AppCompatActivity implements TimePickerDialog.SetTimeListener ,
 DatePickerDialog.setDateListener{

    boolean isEdit = false;
    int taskDbId;

    String taskName;
    String taskType;
    int taskHour;
    int taskMinutes;
    int taskDate;
    int taskMonth;
    boolean isNotify;

    Spinner taskTypeSpinner;
    CheckBox taskNotifyCb;

    boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tasks_detail_page);
        setSupportActionBar(toolbar);

        final EditText taskNameTv = (EditText) findViewById(R.id.name_task_detail_page);
        TextView taskTimeTv = (TextView) findViewById(R.id.time_task_detail_page);
        TextView taskDateTv = (TextView) findViewById(R.id.date_task_detail_page);
        taskNotifyCb = (CheckBox) findViewById(R.id.notify_checkbox_task_detail_page);
        taskTypeSpinner = (Spinner) findViewById(R.id.type_task_detail_page);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.type_create_routine,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskTypeSpinner.setAdapter(arrayAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        taskDbId = intent.getIntExtra(TasksFragment.TASK_DB_ID , -1);

        Cursor cursor = getContentResolver().query(DatabaseContract.TaskContract.buildUriWithId(taskDbId) , null , null , null , null);

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

        if ( !taskName.isEmpty() ){
            values = new ContentValues();
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_NAME , taskName);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_TYPE , taskTypeStr);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_NOTIFY , isNotify);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_TIME_HOUR , taskHour);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_TIME_MINUTES , taskMinutes);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_DATE , taskDate);
            values.put(DatabaseContract.TaskContract.COLUMN_TASK_MONTH , taskMonth);
            int updatedRows = getContentResolver().update(DatabaseContract.TaskContract.buildUriWithId(taskDbId) , values , null , null );
        }else{
            isError = true;
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            if (isEdit){
                updateTask();
            }
            onBackPressed();
            return true;
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
        Intent intent = new Intent(this , LandingPageActivity.class);
        intent.putExtra(ACTIVITY_TO_LANDING_PAGE , TASK_FRAGMENT );
        startActivity(intent);
    }
}
