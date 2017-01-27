package com.example.ganesh.timework.utils;

/**
 * Created by Ganesh Prasad on 11-08-2016.
 */
public class DescriptionTaskListenerModel {

    /**
     * interface for communication
     */
    public interface OnDescriptionTaskDeleteListener{
        void onDescriptionTaskDelete(boolean isEdit, int itemPosition);
    }

    /**
     * Local instance and listener
     */
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
        if (this.mListener != null) mListener = null;
        this.mListener = mListener;
    }

    public void onDescriptionListener(boolean isEdit, int itemPosition){
        if ( mListener != null ){
            mListener.onDescriptionTaskDelete(isEdit , itemPosition);
        }
    }



}
