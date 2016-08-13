package com.example.ganesh.timework.utils;

/**
 * Created by Ganesh Prasad on 11-08-2016.
 */
public class DescriptionNoteListenerModel {

    public interface OnDescriptionNoteDeleteListener{
        void onDescriptionNoteDelete(int itemPosition);
    }

    private static DescriptionNoteListenerModel mInstance;
    private OnDescriptionNoteDeleteListener mListener;

    private DescriptionNoteListenerModel() {}

    public static DescriptionNoteListenerModel getInstance(){
        if ( mInstance == null ){
            mInstance = new DescriptionNoteListenerModel();
        }
        return mInstance;
    }

    public void setListener(OnDescriptionNoteDeleteListener mListener) {
        this.mListener = mListener;
    }

    public void onDescriptionDeleteListener(int itemPosition){
        if ( mListener != null ){
            mListener.onDescriptionNoteDelete(itemPosition);
        }
    }



}
