package com.example.ganesh.timework.adapter;

import android.content.Intent;
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
    onNoteSelectListener listener;

    public NotesRecycleAdapter( List<Notes> _notes , onNoteSelectListener _listener ) {
        this.notes = _notes;
        this.listener = _listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mNotes = notes.get(position);
        holder.id = holder.mNotes.getId();

        final String content = holder.mNotes.getContent();
        final String name = holder.mNotes.getName();
        holder.NotesContentEt.setText( content );
        holder.NotesTitleEt.setText( name );

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( listener != null ){
                    listener.onNoteSelect( name , content , holder.id , holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView NotesTitleEt;
        TextView NotesContentEt;
        View mView;

        int id;

        Notes mNotes;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            NotesTitleEt = (TextView) itemView.findViewById( R.id.et_notes_title_notes_list_item );
            NotesContentEt = (TextView) itemView.findViewById( R.id.et_notes_content_notes_list_item );
        }
    }

    public interface onNoteSelectListener{
        void onNoteSelect( String name , String content , int id , int notePosition );
    }

}
