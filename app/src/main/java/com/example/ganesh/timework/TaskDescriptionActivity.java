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
import com.example.ganesh.timework.utils.Tasks;
import com.example.ganesh.timework.utils.Utilities;

public class TaskDescriptionActivity extends AppCompatActivity implements
    TimePickerDialog.SetTimeListener,
    DatePickerDialog.setDateListener {

  //    For log - debuging
  private static final String LOG_TAG = "Task decs";

  //    To check if the task is edited
  boolean isEdit = false;

  //    The task ID of the task whose description is opened and position of task in RecyclerView
  int taskDbId;
  int mItemPosition;

  //    Views of the activity - Declared global to edit outside of Create methods
  Spinner taskTypeSpinner;
  CheckBox taskNotifyCb;
  TextView taskTimeTv;
  TextView taskDateTv;

  //     tasks details are used in updating the task if edited
  String taskName;
  String taskType;

  int taskHour;
  int taskMinutes;

  int taskDate;
  int taskMonth;
  int taskYear;

  boolean isNotify;

  //    To check if the database update happened without any error
  boolean isError = false;

//    ----------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_description);

//
//        Toolbar implementation
//
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tasks_detail_page);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

//
//        Initialise views
//
    final EditText taskNameTv = (EditText) findViewById(R.id.name_task_detail_page);
    taskTimeTv = (TextView) findViewById(R.id.time_task_detail_page);
    taskDateTv = (TextView) findViewById(R.id.date_task_detail_page);
    taskNotifyCb = (CheckBox) findViewById(R.id.notify_checkbox_task_detail_page);

//
//        Spinner initialise
//
    taskTypeSpinner = (Spinner) findViewById(R.id.type_task_detail_page);
    ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
        .createFromResource(this, R.array.type_create_routine,
            android.R.layout.simple_spinner_item);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    taskTypeSpinner.setAdapter(arrayAdapter);

    /**
     * This part when editing the task
     * task id and recycler view position gotten from intent
     */
//
//        Get the item properties from intent
//
    Intent intent = getIntent();
    taskDbId = intent.getIntExtra(TasksFragment.TASK_DB_ID, -1);
    mItemPosition = intent.getIntExtra(TasksFragment.TASK_RECYCLERVIEW_POSITION, -1);

    if (mItemPosition < 0) {
      Log.d(LOG_TAG, "Invalid position");
      finish();
    }

//
//        Database call
//
    Cursor cursor;
    if (taskDbId > 0) {
      cursor = getContentResolver().query(DatabaseContract.TaskContract.buildUriWithId(taskDbId),
          null, null, null, null);
    } else {
      cursor = null;
      Log.d(LOG_TAG, "id failed. ");
      finish();
    }

    assert cursor != null;

//
//    Tasks call to method that reads cursor
//
    Tasks tasks = Tasks.getTaskFromCursor(cursor);
    cursor.close();
//
//          Database call ends
//

    {
      taskName = tasks.getTaskName();
      taskType = tasks.getTaskType();
      taskDate = tasks.getDate();
      taskMonth = tasks.getMonth();
      taskYear = tasks.getYear();
      taskHour = tasks.getHour();
      taskMinutes = tasks.getMinutes();
      isNotify = tasks.isNotify();
    }

//
//        Filling the Views with data from database
//
    taskNameTv.setText(taskName);
    taskTypeSpinner.setSelection(Constants.getIntForTypeOfRoutine(taskType));

    String time = Utilities.formattedTimeForRoutines(taskHour, taskMinutes);
    String date = Utilities.formattedDayForTask(taskYear, taskMonth, taskDate);

    taskTimeTv.setText(time);
    taskDateTv.setText(date);
    taskNotifyCb.setChecked(isNotify);

//
//    taskNameTv change listener
//
    taskNameTv.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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

    /**
     * Updated to reflect the time in dialog
     * ALso reflect the time in textView after dialog exits
     */

    taskTimeTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isEdit = true;
        DialogFragment fragment = TimePickerDialog
            .newInstance(TaskDescriptionActivity.this, taskHour, taskMinutes);
        fragment.show(getSupportFragmentManager(), "Time fragment");
      }
    });

    taskDateTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isEdit = true;
        DialogFragment fragment = DatePickerDialog
            .newInstance(TaskDescriptionActivity.this, taskDate, taskMonth);
        fragment.show(getSupportFragmentManager(), "Date fragment");
      }
    });

  }

//
//  method to update the task to database
//
  private int updateTask() {
    int typeSelected = taskTypeSpinner.getSelectedItemPosition();
    String taskTypeStr = Constants.getTypeOfRoutine(typeSelected);
    boolean isNotify = taskNotifyCb.isChecked();

    ContentValues values;
    int updatedRows;

    if (!taskName.isEmpty()) {
      values = new ContentValues();
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_NAME, taskName);
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_TYPE, taskTypeStr);
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_NOTIFY, isNotify);
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_TIME_HOUR, taskHour);
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_TIME_MINUTES, taskMinutes);
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_DATE, taskDate);
      values.put(DatabaseContract.TaskContract.COLUMN_TASK_MONTH, taskMonth);
      updatedRows = getContentResolver()
          .update(DatabaseContract.TaskContract.buildUriWithId(taskDbId), values, null, null);
    } else {
      updatedRows = -1;
      isError = true;
    }

    return updatedRows;

  }

//
//  method from TimeDialog
//
  @Override
  public void onSetTime(int hour, int minutes) {
    taskHour = hour;
    taskMinutes = minutes;
    taskTimeTv.setText(hour + ":" + minutes);
  }

//
//  method from DateDialog
//
  @Override
  public void setDate(int year, int month, int day) {
    taskDate = day;
    taskMonth = month;
    taskDateTv.setText(day + "/" + month);
  }

//
//  Contains save, delete and home (X) button - the menu
//
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_task_description_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == android.R.id.home) {
      onBackPressed();
      return false;
    }

    if (id == R.id.delete_tasks_detail_actionbar) {

      int deleteRows = -1;
      boolean isPositive = Utilities.deleteItemRoutine(this);
      if (isPositive) {
//                Log.d(LOG_TAG , " Id " + taskDbId);
        deleteRows = getContentResolver()
            .delete(DatabaseContract.TaskContract.buildUriWithId(taskDbId), null, null);

      }
      if (deleteRows > 0) {
        boolean isEdit = false;

        /**
         * isEdit is used to signal delete operation
         */

        DescriptionTaskListenerModel.getInstance().onDescriptionListener(isEdit, mItemPosition);
        onBackPressed();
        return true;
      } else {
        Log.d(LOG_TAG, " " + deleteRows);
      }
    }

    if (id == R.id.save_task_edit_detail_actionbar) {
      int updateRows = updateTask();
      boolean isEdit = true;

      /**
       * isEdit is used to signal save operation
       * since the listener is same for delete and save
       */

      if (updateRows > 0) {
        DescriptionTaskListenerModel.getInstance().onDescriptionListener(isEdit, mItemPosition);
        onBackPressed();
      } else {
        Log.d(LOG_TAG, updateRows + " Database returned < zero ");
      }
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (isEdit) {

//      TODO do you ask if you want to save or just go back?
//      Just go back I guess
//      We can ask this in next iteration

      super.onBackPressed();
    }
    if (!isError) {
      super.onBackPressed();
    }
  }
}
