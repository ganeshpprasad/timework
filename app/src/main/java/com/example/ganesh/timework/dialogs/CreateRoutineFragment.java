package com.example.ganesh.timework.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ganesh.timework.R;

import java.lang.reflect.Array;

/**
 * Created by Ganesh Prasad on 06-07-2016.
 */
public class CreateRoutineFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialogfragment_create_routine , container , false);

        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_dialogfragment);
        toolbar.setTitle(R.string.dialog_routine_title);
        ((AppCompatActivity) getActivity() ).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity() ).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

        Spinner spinner_type_create_routine = (Spinner) rootView.findViewById(R.id.spinner_type_dialogfragment);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity() ,R.array.type_create_routine ,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_create_routine.setAdapter(arrayAdapter);

        return rootView;
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

        if (id == R.id.create_routine_menu_save) {
//            TODO save routine
            return true;
        }else if (id == android.R.id.home) {
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
