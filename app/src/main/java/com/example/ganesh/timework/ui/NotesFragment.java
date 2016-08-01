package com.example.ganesh.timework.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.NoteDescriptionActivity;
import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.NotesRecycleAdapter;
import com.example.ganesh.timework.data.DatabaseContract.NotesContract;
import com.example.ganesh.timework.dialogs.CreateNotesFragment;
import com.example.ganesh.timework.utils.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements CreateNotesFragment.onNotesCreatedListener ,
NotesRecycleAdapter.onNoteSelectListener{

//    private static final String LOG_TAG = "Notes fragment";

    private OnNotesFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    NotesRecycleAdapter adapter;
    List<Notes> notes;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = getActivity().getContentResolver().query(
                NotesContract.CONTENT_URI ,
                null ,
                null ,
                null ,
                null
        );

        notes = new ArrayList<>();

        assert cursor != null;
        if ( cursor.moveToFirst() ) {
            do {
                int id = cursor.getInt( cursor.getColumnIndexOrThrow(NotesContract._ID) );

                String notesName = cursor.getString( cursor.getColumnIndexOrThrow( NotesContract.COLUMN_NOTES_NAME ) );
                String notesContent = cursor.getString( cursor.getColumnIndexOrThrow( NotesContract.COLUMN_NOTES_CONTENT ) );
                String type = cursor.getString( cursor.getColumnIndexOrThrow( NotesContract.COLUMN_NOTES_TYPE ) );

                int hour = cursor.getInt( cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_HOUR) );
                int minutes = cursor.getInt( cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_MINUTES) );
                int date = cursor.getInt( cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_DATE) );
                int month = cursor.getInt( cursor.getColumnIndexOrThrow(NotesContract.COLUMN_NOTES_CREATED_MONTH) );

                notes.add(new Notes( id , notesName , type ,  notesContent  , hour , minutes , date , month));

            }while ( cursor.moveToNext() );
        }

        cursor.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        adapter = new NotesRecycleAdapter(notes , this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_notes_fragment);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_notes_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add( android.R.id.content ,
                        CreateNotesFragment.newInstance(NotesFragment.this)).commit();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNotesFragmentInteractionListener) {
            mListener = (OnNotesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNotesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNotesFragmentInteractionListener {
        void OnNotesFragmentInteraction();
    }

    @Override
    public void onNotesCreated(Notes note) {
        notes.add(note);
        adapter.notifyItemInserted(notes.size()-1);
    }

    public static final String NOTE_NAME = "note name";
    public static final String NOTE_CONTENT = "note content";
    public static final String NOTE_ID = "note id";

    public static final int DETAIL_REQUEST_CODE = 100;

//    TODO handle this with note _ID from db and new call to db. But if necessary
    @Override
    public void onNoteSelect(String name, String content , int id) {
        Intent intent = new Intent( getActivity() , NoteDescriptionActivity.class);
        intent.putExtra( NOTE_NAME , name );
        intent.putExtra( NOTE_CONTENT , content );
        intent.putExtra( NOTE_ID , id );
        startActivityForResult( intent , DETAIL_REQUEST_CODE );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == DETAIL_REQUEST_CODE ) {
            adapter.notifyItemChanged(resultCode-1);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
