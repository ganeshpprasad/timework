package com.example.ganesh.timework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ganesh.timework.ui.CreateRoutineFragment;
import com.example.ganesh.timework.ui.WeekdayFragment;

public class LandingPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
                    WeekdayFragment.OnWeekdayFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

//        Toolbar implementaion
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_landing_page);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

//        FloatingActionButton implementation
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_landing_page);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRoutineFragment createRoutineFragment = new CreateRoutineFragment();
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).add(
                        android.R.id.content , createRoutineFragment ).addToBackStack(null).commit();
            }
        });

//        Drawer implementation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView implementation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

//        ViewPager implementation
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_landing_page);
        assert viewPager != null;
        viewPager.setAdapter(sectionsPagerAdapter);

//        TableLayout implementation
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_landing_page);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        TODO this inflater adding stuff to dialog fragment
//        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home: {

            }
            case R.id.nav_tasks: {
                Intent intent = new Intent( LandingPageActivity.this , TaskPageActivity.class );
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onWeekdayFragmentInteraction(Uri uri) {

    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_OF_PAGES = 7;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: return WeekdayFragment.newInstance(position+1);
                case 1: return WeekdayFragment.newInstance(position+1);
                case 2: return WeekdayFragment.newInstance(position+1);
                case 3: return WeekdayFragment.newInstance(position+1);
                case 4: return WeekdayFragment.newInstance(position+1);
                case 5: return WeekdayFragment.newInstance(position+1);
                case 6: return WeekdayFragment.newInstance(position+1);
                default: return WeekdayFragment.newInstance(-1);
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch(position){
                case 0: return "Monday";
                case 1: return "Tuesday";
                case 2: return "Wednesday";
                case 3: return "Thursday";
                case 4: return "Friday";
                case 5: return "Saturday";
                case 6: return "Sunday";
            }
            return null;
        }
    }
}
