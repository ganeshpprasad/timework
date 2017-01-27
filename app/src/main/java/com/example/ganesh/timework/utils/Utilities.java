package com.example.ganesh.timework.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.ganesh.timework.data.DatabaseContract;

/**
 * Created by Ganesh Prasad on 08-08-2016.
 */
public class Utilities {

    /**
     * This class is to handle the dialogbox that comes when delete is pressed
     * @param context
     * @return
     */

    public static boolean deleteItemRoutine(final Context context){

        final boolean[] isPositive = new boolean[1];

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                throw new RuntimeException();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete ?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isPositive[0] = true;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isPositive[0] = false;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        AlertDialog dlg = builder.create();
        dlg.show();

        try {
            Looper.loop();
        }catch (RuntimeException re){}

        return isPositive[0];

    }
}
