package com.example.ganesh.timework;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.ui.NotesFragment;
import com.example.ganesh.timework.utils.Notes;

public class NoteDescriptionActivity extends AppCompatActivity {

    EditText noteNameEt;
    EditText noteContentEt;

    String changedContent = "";
    String changedName = "";
    boolean isTextChanged = false;
    int noteId;

    boolean goBack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_description);

        Intent intent = getIntent();
        String noteNameStr = intent.getStringExtra(NotesFragment.NOTE_NAME);
        String noteContentStr = intent.getStringExtra(NotesFragment.NOTE_CONTENT);
        noteId = intent.getIntExtra(NotesFragment.NOTE_ID, -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notes_detail_page);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(noteNameStr);

        noteNameEt = (EditText) findViewById(R.id.notes_details_notes_title);

        if (!noteNameStr.isEmpty()) {
            noteNameEt.setText(noteNameStr);
        } else {
            noteNameEt.setHint("Note Name");
        }

        noteContentEt = (EditText) findViewById(R.id.notes_details_notes_content);
        noteContentEt.setText(noteContentStr);

        noteContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteContentEt.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                isTextChanged = true;
                changedContent = s.toString();
            }
        });

        noteNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteNameEt.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                isTextChanged = true;
                changedName = s.toString();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_notes_detail_action_bar , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        int rows;

        if (id == android.R.id.home) {

//            Either content and/or name has been changed
            if (isTextChanged) {

                ContentValues values = new ContentValues();

//                content cannot be empty so we check it first
                if (!changedContent.isEmpty()) {

//                    check if even name is changed
                    if (!changedName.isEmpty()) {

//                        both are changed
                        values.put(DatabaseContract.NotesContract.COLUMN_NOTES_CONTENT, changedContent);
                        values.put(DatabaseContract.NotesContract.COLUMN_NOTES_NAME, changedName);

                    } else if (!changedContent.isEmpty()) {
//                        name is not changed
                        values.put(DatabaseContract.NotesContract.COLUMN_NOTES_CONTENT, changedContent);
                    }
                    rows = getContentResolver().update(DatabaseContract.NotesContract.buildNotesUriWithId(noteId), values, null, null);
                } else if (!changedName.isEmpty()){
//                    content is not changed
                    values.put(DatabaseContract.NotesContract.COLUMN_NOTES_NAME, changedName);
                    rows = getContentResolver().update(DatabaseContract.NotesContract.buildNotesUriWithId(noteId), values, null, null);
                } else {
                    noteContentEt.requestFocus();
                    goBack = false;
                    Toast.makeText(this, "The content cannot be empty", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (rows == 1) {
                    setResult(noteId);
                }

            }

            return false;

        }

        if ( id == R.id.delete_notes_detail_actionbar ){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int deleteRow = getContentResolver().delete(DatabaseContract.NotesContract.buildNotesUriWithId(noteId) , null , null);
                    if (deleteRow >= 0){
                        finish();
                    }
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setTitle( "Are you sure ?" );

            AlertDialog dialog = builder.create();
            dialog.show();

        }

//        TODO copy contents in action bar

        return true;
    }

    @Override
    public void onBackPressed() {
        if (goBack) {
            super.onBackPressed();
        }
    }
}
