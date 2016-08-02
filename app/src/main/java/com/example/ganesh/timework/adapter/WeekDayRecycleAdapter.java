package com.example.ganesh.timework.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.RoutineItem;
import com.example.ganesh.timework.dialogs.CreateRoutineFragment;

import java.util.List;

/**
 * Created by Ganesh Prasad on 05-07-2016.
 */
public class WeekDayRecycleAdapter extends RecyclerView.Adapter<WeekDayRecycleAdapter.ViewHolder> {

    private static final String LOG_TAG = "weekday adapter";

    public List<RoutineItem.Item> mItemsArray;
    Context mContext;
    OnExtraRoutineListener listener;

    public WeekDayRecycleAdapter(List<RoutineItem.Item> _mitems , Context context , OnExtraRoutineListener _listener ) {
        mItemsArray = _mitems;
        mContext = context;
        listener = _listener;
    }

    @Override
    public WeekDayRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekday_routine_item_layout , parent , false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeekDayRecycleAdapter.ViewHolder holder, final int position) {
        holder.mItem = mItemsArray.get(position);

        holder.mEventName.setText( holder.mItem.eventName );
        String time = holder.mItem.timeHour + ":" + holder.mItem.timeMinutes;
        holder.mTime.setText( time );
        final int routineId = holder.mItem.id;

        holder.mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( holder.extraOptions.getVisibility() == View.VISIBLE ){
                    holder.extraOptions.setVisibility(View.GONE);
                } else {
                    holder.extraOptions.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int deleteRow = mContext.getContentResolver().delete(DatabaseContract.RoutineContract.buildRoutineUri(routineId) ,
                                null , null);
                        if (deleteRow > 0){
                            listener.onDeleteRoutine(position);
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle( "Are you sure ?" );
                builder.setMessage( " It will delete the routine. To remove it from this day use edit. " );
                AlertDialog dlg = builder.create();
                dlg.show();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditRoutine(holder.mItem);
            }
        });

        holder.notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemsArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final ImageView mImageView;
        public final TextView mEventName;
        public final TextView mTime;
        public final ImageButton mOptionsButton;

        public final LinearLayout extraOptions;
        public final ImageButton deleteButton;
        public final ImageButton editButton;
        public final ImageButton notificationButton;

        public RoutineItem.Item mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = (ImageView) mView.findViewById(R.id.image_weekday_routine_item);
            mEventName = (TextView) mView.findViewById(R.id.event_name_weekday_routine_item);
            mTime = (TextView) mView.findViewById(R.id.time_weekday_routine_item);
            mOptionsButton = (ImageButton) mView.findViewById(R.id.more_options_btn_weekday_routine_item);

            extraOptions = (LinearLayout) mView.findViewById( R.id.extra_options_routine_list_item );
            deleteButton = (ImageButton) mView.findViewById(R.id.extra_options_routine_list_item_delete);
            editButton = (ImageButton) mView.findViewById( R.id.extra_options_routine_list_item_edit );
            notificationButton = (ImageButton) mView.findViewById(R.id.extra_options_routine_list_item_notification);
        }
    }

    public interface OnExtraRoutineListener{
        void onDeleteRoutine(int position);
        void onEditRoutine( RoutineItem.Item item );
    }

}
