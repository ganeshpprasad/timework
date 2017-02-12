package com.example.ganesh.timework.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.Routines;
import com.example.ganesh.timework.utils.Utilities;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ganesh Prasad on 05-07-2016.
 */
public class RoutinesRecycleAdapter extends RecyclerView.Adapter<RoutinesRecycleAdapter.ViewHolder> {

//    private static final String LOG_TAG = "weekday adapter";

    // TODO delete routine item
    private List<Routines> mItemsArray;
    private Context mContext;
    private OnExtraRoutineListener listener;

    public RoutinesRecycleAdapter(List<Routines> _mRoutines , Context context , OnExtraRoutineListener _listener ) {
        mItemsArray = _mRoutines;
        mContext = context;
        listener = _listener;
    }

    @Override
    public RoutinesRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_list_item, parent , false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RoutinesRecycleAdapter.ViewHolder holder, int position) {
        holder.mItem = mItemsArray.get(position);

        holder.mEventName.setText( holder.mItem.getRoutineName() );
        String time = Utilities.formattedTimeForRoutines( holder.mItem.getHour() , holder.mItem.getMinutes() );
        holder.mTime.setText( time );
        final int routineId = holder.mItem.getRoutineid();

        holder.mType.setText(holder.mItem.getRoutineType());

        String time_size = holder.mItem.getTime_size();
        if( time_size.equals(Constants.TimeSizes.LESS) ){
            holder.mEventName.setTextSize(20);
        }else if ( time_size.equals(Constants.TimeSizes.MORE) ){
            holder.mEventName.setTextSize(25);
        }else {
            holder.mEventName.setTextSize(35);
        }

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

//TODO ignored for now
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
                            listener.onDeleteRoutine(holder.getAdapterPosition());
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // TODO ask if they want to remove the routine or that task
                builder.setTitle( "Are you sure ?" );
                builder.setMessage( " It will delete the routine. To remove it from this day use edit. " );
                AlertDialog dlg = builder.create();
                dlg.show();
            }
        });

        // TODO ignored for now
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

        final View mView;
//        public final ImageView mImageView;
        final TextView mEventName;
        final TextView mTime;
        final TextView mType;
        final ImageButton mOptionsButton;

        final LinearLayout extraOptions;
        final ImageButton deleteButton;
        final ImageButton editButton;
        final ImageButton notificationButton;

        Routines mItem;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
//            mImageView = (ImageView) mView.findViewById(R.id.image_weekday_routine_item);
            mEventName = (TextView) mView.findViewById(R.id.event_name_weekday_routine_item);
            mTime = (TextView) mView.findViewById(R.id.time_weekday_routine_item);
            mType = (TextView) mView.findViewById(R.id.type_weekday_routine_item);
            mOptionsButton = (ImageButton) mView.findViewById(R.id.more_options_btn_weekday_routine_item);

            extraOptions = (LinearLayout) mView.findViewById( R.id.extra_options_routine_list_item );
            deleteButton = (ImageButton) mView.findViewById(R.id.extra_options_routine_list_item_delete);
            editButton = (ImageButton) mView.findViewById( R.id.extra_options_routine_list_item_edit );
            notificationButton = (ImageButton) mView.findViewById(R.id.extra_options_routine_list_item_notification);
        }
    }
// TODO ignore for now
    public interface OnExtraRoutineListener{
        void onDeleteRoutine(int position);
        void onEditRoutine(Routines item);
    }

}
