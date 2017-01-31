package com.example.ganesh.timework;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ganesh.timework.ui.TodayFragment;
import com.example.ganesh.timework.ui.RoutineFragment;
import com.example.ganesh.timework.ui.TasksFragment;

/**
 * Landing page contains
 * 1. Notes - still debatable feature. Hoping to make tasks without reminders as Notes
 * 2. The dashboard page. But dashboard page is not designed. Yet.
 * THE DRAWER IS NOT WORKING
 */

public class LandingPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RoutineFragment.OnRoutineFragmentInteractionListener,
        TodayFragment.OnNotesFragmentInteractionListener,
        TasksFragment.OnTasksFragmentInteractionListener{

    NavigationView navigationView;

//    private static final String LOG_TAG = "Landing page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

//        NavigationView implementation
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        getSupportFragmentManager().beginTransaction().add(R.id.container_landing_page, TodayFragment.newInstance() ).commit();

//        Toolbar implementation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_landing_page);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Today");

//        Drawer implementation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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

    public static final String FRAGMENT_ROUTINE = "routine";
    public static final String FRAGMENT_TASKS = "tasks";
    public static final String FRAGMENT_NOTES = "notes";

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_landing_page, TodayFragment.newInstance() ).commit();
                getSupportActionBar().setTitle( "Today" );
                break;
            }

            case R.id.nav_routine: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_landing_page, RoutineFragment.newInstance()).commit();
                getSupportActionBar().setTitle("Routines");
                break;
            }

            case R.id.nav_tasks: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_landing_page, TasksFragment.newInstance()).commit();
                getSupportActionBar().setTitle("Tasks");
                break;
            }

            case R.id.nav_tags: {
//                getSupportFragmentManager().beginTransaction().replace()
                getSupportActionBar().setTitle("Tags");
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRoutineFragmentInteraction() {

    }

    @Override
    public void OnNotesFragmentInteraction() {

    }

    @Override
    public void onTasksFragmentInteraction(Uri uri) {

    }

}
