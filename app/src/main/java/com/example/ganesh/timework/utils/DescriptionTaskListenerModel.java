package com.example.ganesh.timework.utils;

/**
 * Created by Ganesh Prasad on 11-08-2016.
 */
public class DescriptionTaskListenerModel {

    public interface OnDescriptionTaskDeleteListener{
        void onDescriptionTaskDelete(int itemPosition);
    }

    private static DescriptionTaskListenerModel mInstance;
    private OnDescriptionTaskDeleteListener mListener;

    private DescriptionTaskListenerModel() {}

    public static DescriptionTaskListenerModel getInstance(){
        if ( mInstance == null ){
            mInstance = new DescriptionTaskListenerModel();
        }
        return mInstance;
    }

    public void setListener(OnDescriptionTaskDeleteListener mListener) {
        this.mListener = mListener;
    }

    public void onDescriptionListener(int itemPosition){
        if ( mListener != null ){
            mListener.onDescriptionTaskDelete(itemPosition);
        }
    }



}
