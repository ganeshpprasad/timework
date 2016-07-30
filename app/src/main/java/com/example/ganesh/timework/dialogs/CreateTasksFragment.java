package com.example.ganesh.timework.dialogs;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.data.DatabaseContract.TaskContract;
import com.example.ganesh.timework.utils.Tasks;

import java.util.Calendar;

/**
 * Created by Ganesh Prasad on 13-07-2016.
 */
public class CreateTasksFragment extends DialogFragment implements TimePickerDialog.SetTimeListener ,
DatePickerDialog.setDateListener{

    private static final String LOG_TAG = "create task";

    OnTaskCreatedListener listener;
    Spinner spinnerTypeCreateTask;
    String taskName;
    int taskType;
    boolean notify;

    int hour;
    int minutes;
    int date;
    int month;

    EditText taskNameEt;
    CheckBox notifyCb;

    public static CreateTasksFragment newInstance( OnTaskCreatedListener listener ){

        CreateTasksFragment fragment = new CreateTasksFragment();
        fragment.listener = listener;
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_create_tasks , container , false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_task_dialog_fragment);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Create new Task");

        setHasOptionsMenu(true);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        String datePicker = date + "/" + month;

        final TextView datePickerTv = (TextView) rootView.findViewById(R.id.select_date_task_dialog_fragment);
        datePickerTv.setText( datePicker );
        datePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(datePickerTv);
            }
        });

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String timePicker = hour + ":" + minutes;

        final TextView timePickerTv = (TextView) rootView.findViewById(R.id.select_time_task_dialog_fragment);
        timePickerTv.setText(timePicker);
        timePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timefragment = TimePickerDialog.newInstance( CreateTasksFragment.this );
                timefragment.show( getFragmentManager() , "time fragment" );
            }
        });

        spinnerTypeCreateTask = (Spinner) rootView.findViewById(R.id.spinner_type_task_dialog_fragment);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity() ,R.array.type_create_routine ,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeCreateTask.setAdapter(arrayAdapter);

        taskNameEt = (EditText) rootView.findViewById(R.id.task_name_task_dialog_fragment);
        notifyCb = (CheckBox) rootView.findViewById(R.id.reminder_task_dialog_fragment);

        return rootView;
    }

    public void showDatePicker(View v){
        DatePickerDialog dateFragment = DatePickerDialog.newInstance( this );
        dateFragment.show(getActivity().getSupportFragmentManager() , "Date Picker");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dialogfragment_menu_create_routine , menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            dismiss();
            return true;
        }

        if (id == R.id.create_routine_menu_save) {
            saveTask();
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveTask(){

        taskName = taskNameEt.getText().toString();
        taskType = spinnerTypeCreateTask.getSelectedItemPosition();
        notify = notifyCb.isChecked();

        int notifyInt;
        String typeString;

        //        storing the int value in database.
        notifyInt = Constants.booleanToInt( notify );

//        getting the type string based on typeSelected
        typeString = Constants.getTypeOfRoutine( taskType );

        ContentValues values = new ContentValues();

        values.put( TaskContract.COLUMN_TASK_NAME , taskName );
        values.put( TaskContract.COLUMN_TASK_TYPE , typeString );
        values.put( TaskContract.COLUMN_TASK_NOTIFY , notifyInt );
        values.put( TaskContract.COLUMN_TASK_DATE , date );
        values.put( TaskContract.COLUMN_TASK_MONTH , month );
        values.put( TaskContract.COLUMN_TASK_TIME_HOUR , hour );
        values.put( TaskContract.COLUMN_TASK_TIME_MINUTES , minutes );

        Tasks task = new Tasks( taskName , typeString , notify );

        Uri uri = getActivity().getContentResolver().insert( TaskContract.CONTENT_URI , values );
        long id = ContentUris.parseId( uri );

        if ( id > 0 ) {
            listener.onTaskCreated(task);
            Log.d( LOG_TAG , " after insertion" + id);
        }

    }

    @Override
    public void onSetTime(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    @Override
    public void setDate(int year, int month, int day) {
        this.date = day;
        this.month = month;
    }

    public interface OnTaskCreatedListener{
        void onTaskCreated(Tasks task);
    }
}
