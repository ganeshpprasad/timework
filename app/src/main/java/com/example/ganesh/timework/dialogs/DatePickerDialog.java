package com.example.ganesh.timework.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Ganesh Prasad on 13-07-2016.
 */
public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    setDateListener dateListener;

    public static DatePickerDialog newInstance(setDateListener listener){
        DatePickerDialog dialog = new DatePickerDialog();
        dialog.dateListener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new android.app.DatePickerDialog(getActivity() , this , year , month , day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dateListener.setDate( year , monthOfYear , dayOfMonth );
    }

    public interface setDateListener{
        void setDate( int year , int month , int day );
    }

}
