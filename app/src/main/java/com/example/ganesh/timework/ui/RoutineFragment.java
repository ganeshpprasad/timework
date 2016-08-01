package com.example.ganesh.timework.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.dialogs.CreateRoutineFragment;

public class RoutineFragment extends Fragment implements  CreateRoutineFragment.onSaveButtonListener{

    private OnRoutineFragmentInteractionListener mListener;
    SectionsPagerAdapter sectionsPagerAdapter;

    CoordinatorLayout coordinatorLayout;

    public RoutineFragment() {
        // Required empty public constructor
    }

    public static RoutineFragment newInstance() {
        RoutineFragment fragment = new RoutineFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_routine, container, false);

//        ViewPager implementation
        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_routine_fragment);
        assert viewPager != null;
        viewPager.setAdapter(sectionsPagerAdapter);

//        TabLayout implementation
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs_routine_fragment);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

//        Floating button implementation
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_routine_fragment);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRoutineFragment createRoutineFragment = CreateRoutineFragment.newInstance(RoutineFragment.this);
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, createRoutineFragment).commit();
            }
        });

        coordinatorLayout = ( CoordinatorLayout ) rootView.findViewById( R.id.coordinator_layout_routine_fragment );

        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRoutineFragmentInteractionListener) {
            mListener = (OnRoutineFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRoutineFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRoutineFragmentInteractionListener {
        void onRoutineFragmentInteraction();
    }

    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_OF_PAGES = 7;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WeekdayFragment.newInstance(position + 1);
                case 1:
                    return WeekdayFragment.newInstance(position + 1);
                case 2:
                    return WeekdayFragment.newInstance(position + 1);
                case 3:
                    return WeekdayFragment.newInstance(position + 1);
                case 4:
                    return WeekdayFragment.newInstance(position + 1);
                case 5:
                    return WeekdayFragment.newInstance(position + 1);
                case 6:
                    return WeekdayFragment.newInstance(position + 1);
                default:
                    return WeekdayFragment.newInstance(-1);
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Monday";
                case 1:
                    return "Tuesday";
                case 2:
                    return "Wednesday";
                case 3:
                    return "Thursday";
                case 4:
                    return "Friday";
                case 5:
                    return "Saturday";
                case 6:
                    return "Sunday";
            }
            return null;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onSaveButton() {
        sectionsPagerAdapter.notifyDataSetChanged();
        Snackbar.make( coordinatorLayout , " Routine created " , Snackbar.LENGTH_SHORT );
    }
}
