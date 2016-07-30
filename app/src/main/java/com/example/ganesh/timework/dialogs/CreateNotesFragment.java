package com.example.ganesh.timework.dialogs;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import android.widget.EditText;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.utils.Notes;

/**
 * Created by Ganesh Prasad on 14-07-2016.
 */
public class CreateNotesFragment extends DialogFragment {

    private static final String LOG_TAG = "Create notes frag";

    EditText titleEt;
    EditText contentEt;

    onNotesCreatedListener listener;

    public static CreateNotesFragment newInstance( onNotesCreatedListener _listener ){
        CreateNotesFragment fragment = new CreateNotesFragment();
        fragment.listener = _listener;
        return fragment ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_create_notes , container , false);

        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_notes_dialog_fragment);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Create New Note");

        ActionBar actionBar = ((AppCompatActivity) getActivity() ).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

        titleEt = (EditText) rootView.findViewById( R.id.title_create_notes_et );
        contentEt = (EditText) rootView.findViewById( R.id.content_create_notes_et );

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
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
        }

        if ( id == R.id.create_routine_menu_save ){
            saveNote();
            dismiss();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNote(){

        String noteName = titleEt.getText().toString();
        String noteContent = contentEt.getText().toString();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NotesContract.COLUMN_NOTES_NAME , noteName);
        values.put( DatabaseContract.NotesContract.COLUMN_NOTES_CONTENT , noteContent );

        Uri insertUri = getActivity().getContentResolver().insert(DatabaseContract.NotesContract.CONTENT_URI , values);
        long id = ContentUris.parseId( insertUri );

        if (id != -1){
            listener.onNotesCreated(new Notes( noteName , null , noteContent , 0 ,0,0,0 ));
        }

    }

    public interface onNotesCreatedListener{
        void onNotesCreated(Notes note);
    }

}
