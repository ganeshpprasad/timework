package com.example.ganesh.timework.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.WeekDayRecycleAdapter;
import com.example.ganesh.timework.data.DatabaseContract.RoutineContract;
import com.example.ganesh.timework.data.RoutineItem;
import com.example.ganesh.timework.dialogs.CreateRoutineFragment;
import com.example.ganesh.timework.utils.Constants.Days;

import java.util.ArrayList;
import java.util.List;

public class WeekdayFragment extends Fragment implements WeekDayRecycleAdapter.OnExtraRoutineListener{

//    private static final String LOG_TAG = "Weekday fragment";
    private static final String ARG_DAY = "day";

    private OnWeekdayFragmentInteractionListener mListener;

    List<RoutineItem.Item> mItemArray;

    public WeekdayFragment() {
        // Required empty public constructor
    }

    public static WeekdayFragment newInstance(int day , OnWeekdayFragmentInteractionListener listener) {
        WeekdayFragment fragment = new WeekdayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY, day);
        fragment.setArguments(args);
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int dayNo = -1;

        if (getArguments() != null) {
            dayNo = getArguments().getInt(ARG_DAY);
        }

        if (dayNo != -1) {

            mItemArray = new ArrayList<>();

            switch (dayNo) {

                case 1:{
                    Uri uri = RoutineContract.buildRoutineUriWithDay(Days.MONDAY);
                    initialiseItemArray(uri);
                    break;
                }
                case 2: {
                    Uri uri = RoutineContract.buildRoutineUriWithDay(Days.TUESDAY);
                    initialiseItemArray(uri);
                    break;
                }
                case 3: {
                    Uri uri = RoutineContract.buildRoutineUriWithDay(Days.WEDNESDAY);
                    initialiseItemArray(uri);
                    break;
                }
                case 4: {
                    Uri uri = RoutineContract.buildRoutineUriWithDay( Days.THURSDAY );
                    initialiseItemArray( uri );
                    break;
                }
                case 5: {
                    Uri uri = RoutineContract.buildRoutineUriWithDay( Days.FRIDAY );
                    initialiseItemArray( uri );
                    break;
                }
                case 6: {
                    Uri uri = RoutineContract.buildRoutineUriWithDay( Days.SATURDAY );
                    initialiseItemArray( uri );
                    break;
                }
                case 7: {
                    Uri uri = RoutineContract.buildRoutineUriWithDay( Days.SUNDAY );
                    initialiseItemArray( uri );
                    break;
                }
                default:
            }

        }

    }

    private void initialiseItemArray(Uri uri){

        String sortOrder = RoutineContract.COLUMN_ROUTINE_TIME_HOUR + " ASC," +
                RoutineContract.COLUMN_ROUTINE_TIME_MINUTES + " ASC";

        Cursor cursor = getContext().getContentResolver().query( uri , null , null , null , sortOrder);
        assert cursor != null;
        if ( cursor.moveToFirst() ) {
            do {

                int id = cursor.getInt( cursor.getColumnIndexOrThrow(RoutineContract._ID) );
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_NAME));
                int eventHour = cursor.getInt(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_TIME_HOUR));
                int eventMinutes = cursor.getInt(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_TIME_MINUTES));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_TYPE));
                int repeat = cursor.getInt(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_NOTIFY));

                RoutineItem.Item mItem = new RoutineItem.Item( id , eventName , eventHour ,eventMinutes , type ,
                        repeat == 1 );

                boolean[] daysBoolArray = new boolean[7];
                String[] daysColumnArray = RoutineContract.getColumnNameDaysArray();

                for ( int i = 0; i < 7; i++ ){
                    daysBoolArray[i] =  cursor.getInt(cursor.getColumnIndexOrThrow(daysColumnArray[i])) == 1 ;
                }
                mItem.setRepeatDays(daysBoolArray);

                mItemArray.add(mItem);

            }while ( cursor.moveToNext() );
        }
        cursor.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekday, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new WeekDayRecycleAdapter(mItemArray , getActivity() , this));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnWeekdayFragmentInteractionListener {
        void onWeekdayFragmentInteractionDeleteRoutine(int position);
        void onWeekdayFragmentInteractionEditRoutine(RoutineItem.Item item);
    }

    @Override
    public void onDeleteRoutine(int position) {
        mListener.onWeekdayFragmentInteractionDeleteRoutine(position);
    }

    @Override
    public void onEditRoutine(RoutineItem.Item item) {
        mListener.onWeekdayFragmentInteractionEditRoutine(item);
    }
}
