package com.example.ganesh.timework.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.TaskDescriptionActivity;
import com.example.ganesh.timework.adapter.TaskRecycleAdapter;
import com.example.ganesh.timework.data.DatabaseContract.TaskContract;
import com.example.ganesh.timework.dialogs.CreateTasksFragment;
import com.example.ganesh.timework.utils.DescriptionTaskListenerModel;
import com.example.ganesh.timework.utils.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements
        CreateTasksFragment.OnNewTaskCreatedListener ,
        TaskRecycleAdapter.OnTasksSelectListener ,
        DescriptionTaskListenerModel.OnDescriptionTaskDeleteListener{

    private OnTasksFragmentInteractionListener mListener;

    public static final String CREATE_TASK_DIALOGFRAGMENT = "create task";
//    private static final String LOG_TAG = "task fragment";

    List<Tasks> tasks;
    TaskRecycleAdapter adapter;

    public TasksFragment() {}

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    /**
     * Database call and fill the tasks list with rows
     * Initialise the singleton listener class for description delete callback
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursor = getActivity().getContentResolver().query(
                TaskContract.CONTENT_URI ,
                null ,
                null ,
                null ,
                null
        );

        tasks = new ArrayList<>();

        assert cursor != null;
        Tasks task;
        for ( int i = cursor.getCount(); i > 0; i -- ){
            task = Tasks.getTaskFromCursor(cursor);
            tasks.add(task);
        }
        cursor.close();
        DescriptionTaskListenerModel.getInstance().setListener(this);

    }

    /**
     * Recycle View and floating button initialisation
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        adapter = new TaskRecycleAdapter(tasks , this);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_tasks_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(CREATE_TASK_DIALOGFRAGMENT).add(android.R.id.content ,
                        CreateTasksFragment.newInstance(TasksFragment.this)).commit();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTasksFragmentInteractionListener) {
            mListener = (OnTasksFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTasksFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnTasksFragmentInteractionListener {
        void onTasksFragmentInteraction(Uri uri);
    }

    @Override
    public void onNewTaskCreated( Tasks task ) {
        tasks.add(task);
        adapter.notifyItemInserted( tasks.size() - 1 );
    }

    /**
     * Handling edit and delete of tasks using a listener - onDescriptionListener(Which handles both)
     */

    public static final String TASK_DB_ID = "task db id";
    public static final String TASK_RECYCLERVIEW_POSITION = "position";

    @Override
    public void onTaskSelect(int taskDbId , int taskRvPosition ) {
        Intent intent = new Intent(getActivity() , TaskDescriptionActivity.class);
        intent.putExtra( TASK_DB_ID , taskDbId );
        intent.putExtra( TASK_RECYCLERVIEW_POSITION , taskRvPosition );
        startActivity(intent);
    }

    private void updateItem(int updatedItemPosition){
        int id = tasks.get(updatedItemPosition).getId();
        if ( id > 0 ){
            tasks.remove(updatedItemPosition);
            Tasks task = Tasks.getTaskFromCursor( getActivity().getContentResolver().query(
                    TaskContract.buildUriWithId(id),
                    null ,
                    null ,
                    null ,
                    null
            ) );

            Log.d("update task list", task.getYear() + " " + task.getMonth());

            tasks.add(updatedItemPosition , task);
            adapter.notifyItemChanged(updatedItemPosition);
        }
    }

    /**
     * To remove item from list
     * @param itemPosition
     */
    @Override
    public void onDescriptionTaskDelete( boolean isEdit, int itemPosition) {
        if (isEdit){
            updateItem(itemPosition);
        } else {
            tasks.remove(itemPosition);
            adapter.notifyItemRemoved(itemPosition);
        }

    }
}
