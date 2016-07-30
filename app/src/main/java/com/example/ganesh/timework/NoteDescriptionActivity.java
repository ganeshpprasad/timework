package com.example.ganesh.timework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.ganesh.timework.ui.NotesFragment;

public class NoteDescriptionActivity extends AppCompatActivity {

    EditText noteNameEt;
    EditText noteContentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_description);

        Intent intent = getIntent();
        String noteNameStr = intent.getStringExtra(NotesFragment.NOTE_NAME);
        String noteContentStr = intent.getStringExtra( NotesFragment.NOTE_CONTENT );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_notes_detail_page );
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( noteNameStr );

        noteNameEt = (EditText) findViewById(R.id.notes_details_notes_title);
        noteNameEt.setText( noteNameStr );

        noteContentEt = (EditText) findViewById( R.id.notes_details_notes_content );
        noteContentEt.setText( noteContentStr );

    }
}
