package com.example.ganesh.timework.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.TaskRecycleAdapter;
import com.example.ganesh.timework.data.ListItemTemp;
import com.example.ganesh.timework.dialogs.CreateTasksFragment;

public class TasksFragment extends Fragment {

    private OnTasksFragmentInteractionListener mListener;
    ListItemTemp.TaskItem[] mTaskItemArray = new ListItemTemp.TaskItem[]{
            new ListItemTemp.TaskItem(1 , "Get a Continental GT" , "0" , "Aug 18" , ListItemTemp.TaskItem.type.PERSONAL , true) ,
            new ListItemTemp.TaskItem(1 , "Get a Macbook pro" , "0" , "Aug 18" , ListItemTemp.TaskItem.type.PERSONAL , true)
    };

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TaskRecycleAdapter(mTaskItemArray));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_tasks_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content ,
                        new CreateTasksFragment()).commit();
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onTasksFragmentInteraction(Uri uri);
    }
}
