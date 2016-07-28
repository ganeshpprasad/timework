package com.example.ganesh.timework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.utils.Notes;

import java.util.List;

/**
 * Created by Ganesh Prasad on 28-07-2016.
 */
public class NotesRecycleAdapter extends RecyclerView.Adapter<NotesRecycleAdapter.ViewHolder> {

    List<Notes> notes;

//    TODO This kinda includes all the adapters - implement the listener to open the detail activity

    public NotesRecycleAdapter( List<Notes> _notes ) {
        this.notes = _notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mNotes = notes.get(position);

        holder.NotesContentEt.setText( holder.mNotes.getContent() );
        holder.NotesTitleEt.setText( holder.mNotes.getName() );

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView NotesTitleEt;
        TextView NotesContentEt;

        Notes mNotes;

        public ViewHolder(View itemView) {
            super(itemView);

            NotesTitleEt = (TextView) itemView.findViewById( R.id.et_notes_title_notes_list_item );
            NotesContentEt = (TextView) itemView.findViewById( R.id.et_notes_content_notes_list_item );
        }
    }
}
