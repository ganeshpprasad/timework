package com.example.ganesh.timework.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.NoteDescriptionActivity;
import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.NotesRecycleAdapter;
import com.example.ganesh.timework.data.DatabaseContract.NotesContract;
import com.example.ganesh.timework.dialogs.CreateNotesFragment;
import com.example.ganesh.timework.utils.DescriptionNoteListenerModel;
import com.example.ganesh.timework.utils.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements
        CreateNotesFragment.OnNewNoteCreatedListener ,
        NotesRecycleAdapter.onNoteSelectListener ,
        DescriptionNoteListenerModel.OnDescriptionNoteDeleteListener{

//    private static final String LOG_TAG = "Notes fragment";

    private OnNotesFragmentInteractionListener mListener;

    /**
     * Both adapter and notes' list is used for refreshing the recycleview on data set changes
     */
    NotesRecycleAdapter adapter;
    List<Notes> notes = null;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    /**
     * Initialise and execute database query. Fill the list of notes item using the query results
     * Initialise the singleton class listener for @NotesDescription activity callback
     * @param savedInstanceState
     */
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
        for ( int i = cursor.getCount() ; i > 0; i--  ){
            Notes newNote = Notes.getNotesFromCursor(cursor);
            notes.add(newNote);
        }
        cursor.close();
        DescriptionNoteListenerModel.getInstance().setListener(this);
    }

    /**
     * RecycleView initialisation
     * Floating button initialise
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        /**
         * Adapter initialisation with List of notes as data set
         */
        adapter = new NotesRecycleAdapter(notes , this);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_notes_fragment);
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
    public void onNewNoteCreated(Notes newNote) {
        notes.add(newNote);
        adapter.notifyItemInserted(notes.size()-1);
    }

    public static final String NOTE_NAME = "note name";
    public static final String NOTE_CONTENT = "note content";
    public static final String NOTE_ID = "note id";
    public static final String NOTE_RECYCLEVIEW_POSITION = "position";

    public static final int DETAIL_REQUEST_CODE = 100;

    @Override
    public void onNoteSelect(String name, String content , int id , int notePosition) {
        Intent intent = new Intent( getActivity() , NoteDescriptionActivity.class);
        intent.putExtra( NOTE_NAME , name );
        intent.putExtra( NOTE_CONTENT , content );
        intent.putExtra( NOTE_ID , id );
        intent.putExtra( NOTE_RECYCLEVIEW_POSITION , notePosition );
        startActivityForResult( intent , DETAIL_REQUEST_CODE );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == DETAIL_REQUEST_CODE && resultCode > 0 ) {
            updateNote(resultCode);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * method to update an note
     * Replace the note with update one by querying from db
     * @param updatedNotePosition
     */
    private void updateNote(int updatedNotePosition){
        int id = notes.get(updatedNotePosition).getId();

        if ( id > 0 ){
            notes.remove(updatedNotePosition);
            Notes note = Notes.getNotesFromCursor( getActivity().getContentResolver().query(
                    NotesContract.buildNotesUriWithId(id),
                    null ,
                    null ,
                    null ,
                    null
            ) );

            notes.add(updatedNotePosition , note);
            adapter.notifyItemChanged(updatedNotePosition);
        }
    }

    @Override
    public void onDescriptionNoteDelete(int itemPosition) {
        notes.remove(itemPosition);
        adapter.notifyItemRemoved(itemPosition);
    }
}
