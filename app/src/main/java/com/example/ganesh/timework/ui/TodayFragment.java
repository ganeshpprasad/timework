package com.example.ganesh.timework.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.TaskDescriptionActivity;
import com.example.ganesh.timework.adapter.RoutinesRecycleAdapter;
import com.example.ganesh.timework.adapter.TaskRecycleAdapterForToday;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.data.RoutineItem;
import com.example.ganesh.timework.dialogs.CreateRoutineFragment;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.DescriptionTaskListenerModel;
import com.example.ganesh.timework.utils.Routines;
import com.example.ganesh.timework.utils.Tasks;

import java.util.ArrayList;
import java.util.List;

//import static com.example.ganesh.timework.ui.TasksFragment.DETAIL_REQUEST_NOTES_CODE;
import static com.example.ganesh.timework.ui.TasksFragment.TASK_DB_ID;
import static com.example.ganesh.timework.ui.TasksFragment.TASK_RECYCLERVIEW_POSITION;

public class TodayFragment extends Fragment implements
        TaskRecycleAdapterForToday.OnTasksSelectListener ,
        RoutinesRecycleAdapter.OnExtraRoutineListener,
        CreateRoutineFragment.OnNewRoutineCreatedListener,
        DescriptionTaskListenerModel.OnDescriptionTaskDeleteListener{

//    private static final String LOG_TAG = "Today fragment";

    private OnNotesFragmentInteractionListener mListener;

    /**
     * Both adapter and notes' list is used for refreshing the recycleview on data set changes
     *
     * There will be two lists - tasks and routines
     *
     */
    TaskRecycleAdapterForToday adapterTasks;
    RoutinesRecycleAdapter adapterRoutines;
    List<Tasks> tasks ;
    List<Routines> routines ;

    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    /**
     * Initialise and execute database query. Fill the list of notes item using the query results
     * Initialise the singleton class listener for @NotesDescription activity callback
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor cursorTasks = getActivity().getContentResolver().query(
                DatabaseContract.TaskContract.CONTENT_URI ,
                null ,
                null ,
                null ,
                null
        );

        String sortOrder = DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_HOUR + " ASC," +
                DatabaseContract.RoutineContract.COLUMN_ROUTINE_TIME_MINUTES + " ASC";

        Cursor cursorRoutines = getActivity().getContentResolver().query(
                DatabaseContract.RoutineContract.buildRoutineUriWithDay(Constants.Days.MONDAY),
                null ,
                null ,
                null ,
                sortOrder
        );

        tasks = new ArrayList<>();
        routines = new ArrayList<>();

        assert cursorTasks != null;
        assert cursorRoutines != null;

        for ( int i = cursorTasks.getCount() ; i > 0; i--  ){
            Tasks newTask = Tasks.getTaskFromCursor(cursorTasks);
            tasks.add(newTask);
        }

        if(cursorRoutines.moveToFirst()){
            do{
                Routines newRoutine = Routines.getRoutinesFromCursor(cursorRoutines);
                routines.add(newRoutine);
            }while(cursorRoutines.moveToNext());
        }

        cursorTasks.close();
        cursorRoutines.close();
    }

    @Override
    public void onResume() {
        DescriptionTaskListenerModel.getInstance().setListener(this);
        super.onResume();
    }

    /**
     * RecycleView initialisation
     * Floating button initialise
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        /**
         * Adapter initialisation with List of notes as data set
         */
        adapterTasks = new TaskRecycleAdapterForToday( tasks , this);
        adapterRoutines = new RoutinesRecycleAdapter( routines , getContext() , this );

        RecyclerView recyclerViewTasks = (RecyclerView) rootView.findViewById(R.id.recycler_view_tasks_fragment);
        recyclerViewTasks.setLayoutManager( new LinearLayoutManager(getActivity()));
        recyclerViewTasks.setAdapter(adapterTasks);

        RecyclerView recyclerViewRoutines = (RecyclerView) rootView.findViewById(R.id.recycler_view_routines_fragment);
        recyclerViewRoutines.setLayoutManager( new LinearLayoutManager(getActivity()));
        recyclerViewRoutines.setAdapter(adapterRoutines);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNotesFragmentInteractionListener) {
            mListener = (OnNotesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNotesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskSelect(int taskDbId, int taskRvPosition) {
        Intent intent = new Intent( getActivity() , TaskDescriptionActivity.class);
        intent.putExtra(TASK_DB_ID , taskDbId);
        intent.putExtra(TASK_RECYCLERVIEW_POSITION, taskRvPosition);
        startActivity(intent);
//        startActivityForResult(intent, DETAIL_REQUEST_NOTES_CODE);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == DETAIL_REQUEST_NOTES_CODE && resultCode > 0){
//            updateTask(resultCode);
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void updateTask(int updatedTaskPosition){

        int id = tasks.get(updatedTaskPosition).getId();

        if( id > 0 ){
            tasks.remove(updatedTaskPosition);

            Tasks task = Tasks.getTaskFromCursor( getActivity().getContentResolver().query(
                    DatabaseContract.TaskContract.buildUriWithId(id),
                    null,
                    null,
                    null,
                    null) );
            tasks.add( updatedTaskPosition , task );
            adapterTasks.notifyItemChanged(updatedTaskPosition);
        }

    }

    public interface OnNotesFragmentInteractionListener {
        void OnNotesFragmentInteraction();
    }

    @Override
    public void onDeleteRoutine(int position) {
        routines.remove(position);
        adapterRoutines.notifyItemRemoved(position);
    }

    @Override
    public void onEditRoutine(Routines item) {
        getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content ,
                CreateRoutineFragment.newInstance(item, this)).commit();
    }

    @Override
    public void onNewRoutineCreated() {
        adapterRoutines.notifyDataSetChanged();
    }

    @Override
    public void onDescriptionTaskDelete(boolean isEdit, int itemPosition) {

        if (isEdit){
            updateTask(itemPosition);
        } else {
            tasks.remove(itemPosition);
            adapterTasks.notifyDataSetChanged();
        }
    }
}
