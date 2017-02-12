package com.example.ganesh.timework.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.TaskRecycleAdapter;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.Tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh Prasad on 12-02-2017.
 */

public class TagsFragment extends Fragment implements TaskRecycleAdapter.OnTasksSelectListener {

    public TagsFragment() {}

    public static TagsFragment newInstance() {
        TagsFragment tagsFragment = new TagsFragment();
        return tagsFragment;
    }

    List<Tasks> taskHobby;
    List<Tasks> taskPersonal;
    List<Tasks> taskWork;

    TaskRecycleAdapter adapterHobby;
    TaskRecycleAdapter adapterPersonal;
    TaskRecycleAdapter adapterWork;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String sortOrder = DatabaseContract.TaskContract.COLUMN_TASK_MONTH + " ASC," +
                DatabaseContract.TaskContract.COLUMN_TASK_DATE + " ASC," +
                DatabaseContract.TaskContract.COLUMN_TASK_TIME_HOUR + " ASC," +
                DatabaseContract.TaskContract.COLUMN_TASK_TIME_MINUTES + " ASC";

        /**
         * Hobby tasks db call
         * and list definition
         */
        Cursor cursorHobby = getActivity().getContentResolver().query(
                DatabaseContract.TaskContract.buildUriWithType(Constants.RoutineTypes.HOBBY),
                null,
                null,
                null,
                sortOrder
        ) ;

        taskHobby = new ArrayList<>();

        for (int i = 0; i< cursorHobby.getCount(); i++){
            taskHobby.add( Tasks.getTaskFromCursor(cursorHobby) );
        }

        Cursor cursorPersonal = getActivity().getContentResolver().query(
                DatabaseContract.TaskContract.buildUriWithType(Constants.RoutineTypes.PERSONAL),
                null,
                null,
                null,
                sortOrder
        );

        taskPersonal = new ArrayList<>();

        for (int i = 0; i < cursorPersonal.getCount(); i++){
            taskPersonal.add(Tasks.getTaskFromCursor(cursorPersonal));
        }

        Cursor cursorWork = getActivity().getContentResolver().query(
                DatabaseContract.TaskContract.buildUriWithType(Constants.RoutineTypes.WORK),
                null,
                null,
                null,
                sortOrder
        );

        taskWork = new ArrayList<>();

        for ( int i = 0; i < cursorWork.getCount(); i++){
            taskWork.add( Tasks.getTaskFromCursor(cursorWork) );
        }

        cursorHobby.close();
        cursorPersonal.close();
        cursorWork.close();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tags, container, false);

        adapterHobby = new TaskRecycleAdapter( taskHobby, this );
        adapterPersonal = new TaskRecycleAdapter(taskPersonal, this);
        adapterWork = new TaskRecycleAdapter( taskWork, this );

        RecyclerView recyclerViewHobby = (RecyclerView) rootView.findViewById(R.id.recycler_view_tags_hobby);
        recyclerViewHobby.setLayoutManager( new LinearLayoutManager(getContext()));
        recyclerViewHobby.setAdapter(adapterHobby);

        RecyclerView recyclerViewPersonal = (RecyclerView) rootView.findViewById(R.id.recycler_view_tags_personal);
        recyclerViewPersonal.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPersonal.setAdapter(adapterPersonal);

        RecyclerView recyclerViewWork = (RecyclerView) rootView.findViewById(R.id.recycler_view_tags_work);
        recyclerViewWork.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWork.setAdapter(adapterWork);

        return rootView;
    }

    @Override
    public void onTaskSelect(int taskDbId, int taskRvPosition) {

    }
}
