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
import com.example.ganesh.timework.utils.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements CreateTasksFragment.OnTaskCreatedListener ,
TaskRecycleAdapter.OnTasksClickListener{

    private OnTasksFragmentInteractionListener mListener;

    private static final String LOG_TAG = "task fragment";

    List<Tasks> tasks;
    TaskRecycleAdapter adapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    String taskName;
    String taskType;
    boolean notify;
    int id;

    int hour;
    int minutes;
    int date;
    int month;

    View rootView;

    InputMethodManager imm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm  = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        Cursor cursor = getActivity().getContentResolver().query(
                TaskContract.CONTENT_URI ,
                null ,
                null ,
                null ,
                null
        );

        tasks = new ArrayList<>();

        assert cursor != null;

        if ( cursor.moveToFirst() ){
            do {

                taskName = cursor.getString( cursor.getColumnIndexOrThrow( TaskContract.COLUMN_TASK_NAME ) );
                taskType = cursor.getString( cursor.getColumnIndexOrThrow( TaskContract.COLUMN_TASK_TYPE ) );

                hour = cursor.getInt( cursor.getColumnIndexOrThrow( TaskContract.COLUMN_TASK_TIME_HOUR ) );
                minutes = cursor.getInt( cursor.getColumnIndexOrThrow( TaskContract.COLUMN_TASK_TIME_MINUTES ) );
                date = cursor.getInt( cursor.getColumnIndexOrThrow( TaskContract.COLUMN_TASK_DATE ) );
                month = cursor.getInt( cursor.getColumnIndexOrThrow( TaskContract.COLUMN_TASK_MONTH ) );

                notify = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.COLUMN_TASK_NOTIFY)) == 1;
                id = cursor.getInt( cursor.getColumnIndexOrThrow(TaskContract._ID) );

//                TODO do you need notify info here??
                Tasks task = new Tasks( taskName , taskType , notify );

                task.setId(id);
                task.setDate(date);
                task.setMonth(month);
                task.setHour(hour);
                task.setMinutes(minutes);

                tasks.add( task );

            }while (cursor.moveToNext());
        }else {
            Log.d( "task frag" , " empty db" );
        }
        cursor.close();

    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        adapter = new TaskRecycleAdapter(tasks , getContext() , this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_tasks_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content ,
                        CreateTasksFragment.newInstance(TasksFragment.this)).commit();
            }
        });

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTasksFragmentInteraction(uri);
        }
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
    public void onTaskCreated( Tasks task ) {
        tasks.add(task);
        adapter.notifyItemInserted( tasks.size() - 1 );
    }

    public static final String TASK_DB_ID = "task db id";

    @Override
    public void onTaskClick(int taskDbId) {
        Intent intent = new Intent(getActivity() , TaskDescriptionActivity.class);
        intent.putExtra( TASK_DB_ID , taskDbId );
        startActivity(intent);
    }
}
