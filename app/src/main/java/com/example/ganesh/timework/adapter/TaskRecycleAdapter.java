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

/**
 * Created by Ganesh Prasad on 07-07-2016.
 */
public class TaskRecycleAdapter extends RecyclerView.Adapter<TaskRecycleAdapter.ViewHolder> {

    ListItemTemp.TaskItem[] mTaskItemArray;

    public TaskRecycleAdapter(ListItemTemp.TaskItem[] mItems) {
        mTaskItemArray = mItems;
    }

    @Override
    public TaskRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_list_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskRecycleAdapter.ViewHolder holder, int position) {
        holder.mItem = mTaskItemArray[position];

        holder.taskName.setText(holder.mItem.taskName);
        holder.taskTime.setText(holder.mItem.taskTime);
    }

    @Override
    public int getItemCount() {
        return mTaskItemArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView typeImage;
        public final TextView taskName;
        public final TextView taskTime;
        public final ImageButton moreOptions;

        public ListItemTemp.TaskItem mItem;

        public ViewHolder(View itemView) {

            super(itemView);

            typeImage = (ImageView) itemView.findViewById(R.id.image_task_list_item);
            taskName = (TextView) itemView.findViewById(R.id.task_name_task_list_item);
            taskTime = (TextView) itemView.findViewById(R.id.task_time_list_item);
            moreOptions = (ImageButton) itemView.findViewById(R.id.more_options_btn_task_list_item);

        }
    }
}
