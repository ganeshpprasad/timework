package com.example.ganesh.timework.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Ganesh Prasad on 23-07-2016.
 */
public class TimePickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    SetTimeListener setTimeListener;

    public TimePickerDialog(  ) {
        super();
    }

    public static TimePickerDialog newInstance(SetTimeListener listener) {

        TimePickerDialog timePickerDialog = new TimePickerDialog();

        timePickerDialog.setTimeListener = listener;

        return timePickerDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get( Calendar.HOUR_OF_DAY );
        int minutes = calendar.get( Calendar.MINUTE );

        return new android.app.TimePickerDialog( getActivity() , this , hour , minutes ,
                android.text.format.DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        setTimeListener.onSetTime( hourOfDay , minute );

    }



    public interface SetTimeListener {

        void onSetTime( int hour , int minutes );

    }

}
