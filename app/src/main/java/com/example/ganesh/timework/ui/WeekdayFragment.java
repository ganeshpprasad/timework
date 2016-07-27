package com.example.ganesh.timework.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.WeekDayRecycleAdapter;
import com.example.ganesh.timework.data.DatabaseContract.RoutineContract;
import com.example.ganesh.timework.data.DatabaseHelper;
import com.example.ganesh.timework.data.ListItemTemp;
import com.example.ganesh.timework.dialogs.CreateRoutineFragment;
import com.example.ganesh.timework.utils.Constants.Days;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class WeekdayFragment extends Fragment implements CreateRoutineFragment.onSaveButtonListener  {

    private static final String ARG_DAY = "day";

    private OnWeekdayFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    List<ListItemTemp.Item> mItemArray;

    public WeekdayFragment() {
        // Required empty public constructor
    }

    public static WeekdayFragment newInstance(int day) {
        WeekdayFragment fragment = new WeekdayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    private int dayNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        Cursor cursor = getContext().getContentResolver().query( uri , null , null , null , null);
        assert cursor != null;
        if ( cursor.moveToFirst() ) {
            do {

                String eventName = cursor.getString(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_NAME));
                int eventHour = cursor.getInt(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_TIME_HOUR));
                int eventMinutes = cursor.getInt(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_TIME_MINUTES));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_TYPE));
                int repeat = cursor.getInt(cursor.getColumnIndexOrThrow(RoutineContract.COLUMN_ROUTINE_NOTIFY));

                mItemArray.add( new ListItemTemp.Item( eventName , eventHour ,eventMinutes , type ,
                        repeat == 1 ) );

            }while ( cursor.moveToNext() );
        }
        cursor.close();

    }

    WeekDayRecycleAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekday, container, false);

        recyclerAdapter = new WeekDayRecycleAdapter(mItemArray);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerAdapter);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onWeekdayFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWeekdayFragmentInteractionListener) {
            mListener = (OnWeekdayFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnWeekdayFragmentInteractionListener {
        // TODO: Update argument type and name
        void onWeekdayFragmentInteraction(Uri uri);
    }

    @Override
    public void onSaveButton() {
        this.recyclerAdapter.notifyDataSetChanged();
    }
}
