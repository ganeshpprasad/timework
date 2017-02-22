package com.example.ganesh.timework.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.TaskDescriptionActivity;
import com.example.ganesh.timework.adapter.RoutinesRecycleAdapter;
import com.example.ganesh.timework.adapter.TaskRecycleAdapterForToday;
import com.example.ganesh.timework.data.DatabaseContract;
import com.example.ganesh.timework.dialogs.CreateRoutineFragment;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.DescriptionTaskListenerModel;
import com.example.ganesh.timework.utils.Routines;
import com.example.ganesh.timework.utils.Tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.ganesh.timework.ui.TasksFragment.TASK_DB_ID;
import static com.example.ganesh.timework.ui.TasksFragment.TASK_RECYCLERVIEW_POSITION;

public class TodayFragment extends Fragment implements
        TaskRecycleAdapterForToday.OnTasksSelectListener ,
        RoutinesRecycleAdapter.OnExtraRoutineListener,
        CreateRoutineFragment.OnNewRoutineCreatedListener,
        DescriptionTaskListenerModel.OnDescriptionTaskDeleteListener{

//    private static final String LOG_TAG = "Today fragment";

//    Might need to use this listener to communicate with activity
    private OnNotesFragmentInteractionListener mListener;

    /**
     * Both adapter and notes' list is used for refreshing the recycleview on data set changes
     * There will be two lists - tasks and routines
     *
     */
    TaskRecycleAdapterForToday adapterTasks;
    RoutinesRecycleAdapter adapterRoutines;
    List<Tasks> tasks ;
    List<Routines> routines ;

    public TodayFragment() {}

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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayOfWeek = sdf.format(c.getTime());

        Cursor cursorRoutines = getActivity().getContentResolver().query(
                DatabaseContract.RoutineContract.buildRoutineUriWithDay(Constants.getIntWeekday(dayOfWeek)),
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
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        final RelativeLayout todayTasksRl = (RelativeLayout) rootView.findViewById(R.id.today_tasks);

        if( tasks.size() == 0 ){
            rootView.findViewById(R.id.today_tasks_no_tasks_textview).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.recycler_view_tasks_fragment).setVisibility(View.INVISIBLE);

            todayTasksRl.setVisibility(View.GONE);
        }

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

        /**
         * Implementing tasks hide and show
         * text view on click toggles tasks' list
         */

        TextView taskTitleTv = (TextView) rootView.findViewById(R.id.today_tasks_title);
        taskTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( todayTasksRl.getVisibility() == View.GONE ){
                    todayTasksRl.setVisibility(View.VISIBLE);
                } else {
                    todayTasksRl.setVisibility(View.GONE);
                }
            }
        });

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

    /**
     * Method to open task description activity after a task is selected
     * @param taskDbId is the database ID of the task
     * @param taskRvPosition is the position of the task in recycler view - same as list
     */
    @Override
    public void onTaskSelect(int taskDbId, int taskRvPosition) {
        Intent intent = new Intent( getActivity() , TaskDescriptionActivity.class);
        intent.putExtra(TASK_DB_ID , taskDbId);
        intent.putExtra(TASK_RECYCLERVIEW_POSITION, taskRvPosition);
        startActivity(intent);
    }

    /**
     * updates the task if it is edited in the description activity
     * @param updatedTaskPosition
     */
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

    /**
     * Deleted the task from list and recycler view
     * @param position
     */
    @Override
    public void onDeleteRoutine(int position) {
        routines.remove(position);
        adapterRoutines.notifyItemRemoved(position);
    }

    /**
     * Opens the create routine dialog to edit the routine
     * @param item
     */
    @Override
    public void onEditRoutine(Routines item) {
        getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content ,
                CreateRoutineFragment.newInstance(item, this)).commit();
    }

    /**
     * Callback from routine adapter after a new routine is created
     */
    @Override
    public void onNewRoutineCreated() {
        adapterRoutines.notifyDataSetChanged();
    }

    /**
     * Listener method for task edit and delete
     * @param isEdit
     * @param itemPosition
     */
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
