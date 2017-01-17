package com.example.ganesh.timework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.utils.Tasks;

import java.util.List;

public class TaskRecycleAdapterForToday extends RecyclerView.Adapter<TaskRecycleAdapterForToday.ViewHolder> {

//    private static final String LOG_TAG = "task adapter";

    List<Tasks> tasksList;
    OnTasksSelectListener listener;

    public TaskRecycleAdapterForToday(List<Tasks> mItems , OnTasksSelectListener listener) {
        tasksList = mItems;
        this.listener = listener;
    }

    @Override
    public TaskRecycleAdapterForToday.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_list_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskRecycleAdapterForToday.ViewHolder holder, int position) {
        holder.mItem = tasksList.get(position);
        String time = holder.mItem.getHour() + ":" + holder.mItem.getMinutes();

        final int taskDbId = holder.mItem.getId();
        holder.taskName.setText(holder.mItem.getTaskName());
        holder.taskTime.setText(time);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskSelect(taskDbId , holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView typeImage;
        public final TextView taskName;
        public final TextView taskTime;
        public final View mView;

        public Tasks mItem;

        public ViewHolder(View itemView) {

            super(itemView);

            mView = itemView;
            typeImage = (ImageView) itemView.findViewById(R.id.image_task_list_item);
            taskName = (TextView) itemView.findViewById(R.id.task_name_task_list_item);
            taskTime = (TextView) itemView.findViewById(R.id.task_time_list_item);

        }
    }

    public interface OnTasksSelectListener{
        void onTaskSelect(int taskDbId, int taskRvPosition);
    }
}
