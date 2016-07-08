package com.example.ganesh.timework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.data.ListItemTemp;

import org.w3c.dom.Text;

/**
 * Created by Ganesh Prasad on 05-07-2016.
 */
public class WeekDayRecycleAdapter extends RecyclerView.Adapter<WeekDayRecycleAdapter.ViewHolder> {

    public ListItemTemp.Item[] mItemsArray;

    public WeekDayRecycleAdapter( ListItemTemp.Item[] _mitems ) {
        mItemsArray = _mitems;
    }

    @Override
    public WeekDayRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekday_routine_item_layout , parent , false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeekDayRecycleAdapter.ViewHolder holder, int position) {
        holder.mItem = mItemsArray[position];

        holder.mEventName.setText(holder.mItem.eventName);
        holder.mTime.setText(holder.mItem.time);

    }

    @Override
    public int getItemCount() {
        return mItemsArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final ImageView mImageView;
        public final TextView mEventName;
        public final TextView mTime;
        public final ImageButton mOptionsButton;

        public ListItemTemp.Item mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = (ImageView) mView.findViewById(R.id.image_weekday_routine_item);
            mEventName = (TextView) mView.findViewById(R.id.event_name_weekday_routine_item);
            mTime = (TextView) mView.findViewById(R.id.time_weekday_routine_item);
            mOptionsButton = (ImageButton) mView.findViewById(R.id.more_options_btn_weekday_routine_item);
        }
    }
}
