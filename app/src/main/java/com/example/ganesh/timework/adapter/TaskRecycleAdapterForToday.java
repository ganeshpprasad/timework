package com.example.ganesh.timework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.utils.Tasks;
import com.example.ganesh.timework.utils.Utilities;

import java.util.List;

public class TaskRecycleAdapterForToday extends
    RecyclerView.Adapter<TaskRecycleAdapterForToday.ViewHolder> {

//    private static final String LOG_TAG = "task adapter";

  List<Tasks> tasksList;
  OnTasksSelectListener listener;

  public TaskRecycleAdapterForToday(List<Tasks> mItems, OnTasksSelectListener listener) {
    tasksList = mItems;
    this.listener = listener;
  }

  @Override
  public TaskRecycleAdapterForToday.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.tasks_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final TaskRecycleAdapterForToday.ViewHolder holder, int position) {
    holder.mItem = tasksList.get(position);
    String time = Utilities
        .formattedTimeForRoutines(holder.mItem.getHour(), holder.mItem.getMinutes());
    String day = Utilities.formattedDayForTask(holder.mItem.getYear(), holder.mItem.getMonth(),
        holder.mItem.getDate());

    final int taskDbId = holder.mItem.getId();
    holder.taskName.setText(holder.mItem.getTaskName());
    holder.taskTime.setText(time);
    holder.taskDate.setText(day);

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onTaskSelect(taskDbId, holder.getAdapterPosition());
      }
    });

    holder.taskCompletedButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//                TODO handle task completed
        holder.taskCompletedButton.setSelected(!holder.taskCompletedButton.isSelected());

      }
    });

  }

  @Override
  public int getItemCount() {
    return tasksList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    //        public final ImageView typeImage;
    private final TextView taskName;
    private final TextView taskTime;
    private final TextView taskDate;
    private final ImageButton taskCompletedButton;
    private final View mView;
//        private final ToggleButton checkTask;

    private Tasks mItem;

    private ViewHolder(View itemView) {

      super(itemView);

      mView = itemView;
//            typeImage = (ImageView) itemView.findViewById(R.id.image_task_list_item);
      taskName = (TextView) itemView.findViewById(R.id.task_name_task_list_item);
      taskTime = (TextView) itemView.findViewById(R.id.task_time_list_item);
      taskDate = (TextView) itemView.findViewById(R.id.task_Date_list_item);
      taskCompletedButton = (ImageButton) itemView.findViewById(R.id.task_check_item);
    }
  }

  public interface OnTasksSelectListener {

    void onTaskSelect(int taskDbId, int taskRvPosition);
  }
}
