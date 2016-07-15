package com.example.ganesh.timework.dialogs;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ganesh.timework.R;

/**
 * Created by Ganesh Prasad on 13-07-2016.
 */
public class CreateTasksFragment extends DialogFragment {

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

        final TextView datePickerTv = (TextView) rootView.findViewById(R.id.select_date_task_dialog_fragment);
        datePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(datePickerTv);
            }
        });

        return rootView;
    }

    public void showDatePicker(View v){
        DialogFragment dateFragment = new DatePickerDialog();
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
//            TODO savetask
        }

        return super.onOptionsItemSelected(item);
    }
}
