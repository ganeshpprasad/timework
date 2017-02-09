package com.example.ganesh.timework.dialogs;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.data.DatabaseContract.TaskContract;
import com.example.ganesh.timework.utils.Tasks;
import com.example.ganesh.timework.utils.Utilities;

import java.util.Calendar;

/**
 * Created by Ganesh Prasad on 13-07-2016.
 */
public class CreateTasksFragment extends DialogFragment implements TimePickerDialog.SetTimeListener,
        DatePickerDialog.setDateListener {

    private static final String LOG_TAG = "create task";

    /**
     * variables initialised here to handle the soft keyboard
     */
     // TODO soft keyboard not handled
    View rootView;
    InputMethodManager imm;

    OnNewTaskCreatedListener listener;

    EditText taskNameEt;
    CheckBox notifyCb;
    Spinner spinnerTypeCreateTask;
    TextView datePickerTv;
    TextView timePickerTv;

    int hour;
    int minutes;

    int day;
    int month;
    int year;

    public static CreateTasksFragment newInstance(OnNewTaskCreatedListener _listener){
        CreateTasksFragment fragment = new CreateTasksFragment();
        fragment.listener = _listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialogfragment_create_tasks, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_task_dialog_fragment);
        toolbar.setTitle("Create new Task");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                hideSoftKeyboard();
            }
        });

        toolbar.inflateMenu(R.menu.dialogfragment_menu_create_routine);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if (id == android.R.id.home) {
                    dismiss();
                    hideSoftKeyboard();
                }

                if (id == R.id.create_routine_menu_save) {
                    saveTask();
                    dismiss();
                    hideSoftKeyboard();
                }

                return false;
            }
        });

        setHasOptionsMenu(true);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        Log.d(LOG_TAG, hour + " " + minutes);

        timePickerTv = (TextView) rootView.findViewById(R.id.select_time_task_dialog_fragment);
        timePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timefragment = TimePickerDialog.newInstance(CreateTasksFragment.this);
                timefragment.show(getFragmentManager(), "time fragment");
            }
        });
        updateTimeTv(hour, minutes);

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        datePickerTv = (TextView) rootView.findViewById(R.id.select_date_task_dialog_fragment);
        datePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(datePickerTv);
            }
        });
        updateDateTv(year, month, day);

        spinnerTypeCreateTask = (Spinner) rootView.findViewById(R.id.spinner_type_task_dialog_fragment);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.type_create_routine,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeCreateTask.setAdapter(arrayAdapter);

        taskNameEt = (EditText) rootView.findViewById(R.id.task_name_task_dialog_fragment);
        notifyCb = (CheckBox) rootView.findViewById(R.id.reminder_task_dialog_fragment);

        return rootView;
    }

    public void showDatePicker(View v) {
        DatePickerDialog dateFragment = DatePickerDialog.newInstance(this);
        dateFragment.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void hideSoftKeyboard() {
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }

    private void saveTask() {

        Tasks task;

        long id;
        String taskName;
        int taskType;
        boolean notify;

        int notifyInt;
        String typeString;

        taskName = taskNameEt.getText().toString();
        taskType = spinnerTypeCreateTask.getSelectedItemPosition();
        notify = notifyCb.isChecked();

//        storing the int value in database.
        notifyInt = Constants.booleanToInt(notify);

//        getting the type string based on typeSelected
        typeString = Constants.getTypeOfRoutine(taskType);

        ContentValues values = new ContentValues();
        values.put(TaskContract.COLUMN_TASK_NAME, taskName);
        values.put(TaskContract.COLUMN_TASK_TYPE, typeString);
        values.put(TaskContract.COLUMN_TASK_NOTIFY, notifyInt);
        values.put(TaskContract.COLUMN_TASK_DATE, day);
        values.put(TaskContract.COLUMN_TASK_MONTH, month);
        values.put(TaskContract.COLUMN_TASK_YEAR, year);
        values.put(TaskContract.COLUMN_TASK_TIME_HOUR, hour);
        values.put(TaskContract.COLUMN_TASK_TIME_MINUTES, minutes);

        if (!taskName.isEmpty()) {

            task = new Tasks(taskName, typeString, notify);
            task.setDate(day);
            task.setMonth(month);
            task.setHour(hour);
            task.setMinutes(minutes);

            Uri uri = getActivity().getContentResolver().insert(TaskContract.CONTENT_URI, values);
            Log.d(LOG_TAG, uri.toString());
            id = ContentUris.parseId(uri);
            if (id > 0) {
                task.setId((int)id);
                listener.onNewTaskCreated(task);
            }

        } else {
            taskNameEt.requestFocus();
            Toast.makeText( getActivity() , "The task name cannot be empty" , Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void onSetTime(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
        updateTimeTv(hour, minutes);
    }

    @Override
    public void setDate(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        updateDateTv(year, month, day);
    }

    public void updateTimeTv(int hour, int minutes){
        String time = Utilities.formattedTimeForRoutines(hour,minutes);
        Log.d(LOG_TAG, time);
        timePickerTv.setText(time);
    }

    public void updateDateTv(int year, int month, int day){
        String date = Utilities.formattedDayForTask(year, month, day);
        datePickerTv.setText(date);
    }

    public interface OnNewTaskCreatedListener {
        void onNewTaskCreated(Tasks task);
    }
}
