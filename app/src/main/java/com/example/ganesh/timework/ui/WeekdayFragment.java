package com.example.ganesh.timework.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.WeekDayRecycleAdapter;
import com.example.ganesh.timework.data.ListItemTemp;

public class WeekdayFragment extends Fragment {

    private static final String ARG_DAY = "day";
//    private static final String ARG_PARAM2 = "param2";

    private int dayNo;
//    private String mParam2;

    private OnWeekdayFragmentInteractionListener mListener;

    /**
     * We gotta set the source for the recycler view here
     * but we'll use a static data set for now but switch
     * according to the day_no
     */
    RecyclerView recyclerView;
    ListItemTemp.Item[] mItemArray;

    ListItemTemp.Item[] mItemArrayM = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS),
            new ListItemTemp.Item(1, "Meet Boss", "7:00 PM", ListItemTemp.Item.type.WORK, ListItemTemp.Item.repeat.NONE),
    };
    ListItemTemp.Item[] mItemArrayTu = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS),
            new ListItemTemp.Item(1, "Meet Boss", "7:00 PM", ListItemTemp.Item.type.WORK, ListItemTemp.Item.repeat.NONE),
    };
    ListItemTemp.Item[] mItemArrayW = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS),
            new ListItemTemp.Item(1, "Meet Boss", "7:00 PM", ListItemTemp.Item.type.WORK, ListItemTemp.Item.repeat.NONE),
    };
    ListItemTemp.Item[] mItemArrayTh = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS)
    };
    ListItemTemp.Item[] mItemArrayF = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS)
    };
    ListItemTemp.Item[] mItemArraySa = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS)
    };
    ListItemTemp.Item[] mItemArraySu = new ListItemTemp.Item[]{
            new ListItemTemp.Item(1, "Gym", "7:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Medicine", "9:00 AM", ListItemTemp.Item.type.PERSONAL, ListItemTemp.Item.repeat.ALLDAYS),
            new ListItemTemp.Item(1, "Practice guitar", "4:00 PM", ListItemTemp.Item.type.HOBBIES, ListItemTemp.Item.repeat.WEEKDAYS)
    };

    public WeekdayFragment() {
        // Required empty public constructor
    }

    public static WeekdayFragment newInstance(int day) {
        WeekdayFragment fragment = new WeekdayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY, day);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dayNo = getArguments().getInt(ARG_DAY);
        }

        if (dayNo != -1) {

            switch (dayNo) {

                case 1:
                    mItemArray = mItemArrayM;
                    break;
                case 2:
                    mItemArray = mItemArrayTu;
                    break;
                case 3:
                    mItemArray = mItemArrayW;
                    break;
                case 4:
                    mItemArray = mItemArrayTh;
                    break;
                case 5:
                    mItemArray = mItemArrayF;
                    break;
                case 6:
                    mItemArray = mItemArraySa;
                    break;
                case 7:
                    mItemArray = mItemArraySu;
                    break;
                default:
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekday, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new WeekDayRecycleAdapter(mItemArray));
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
}
