package com.example.ganesh.timework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ganesh.timework.data.DatabaseHelper;
import com.example.ganesh.timework.ui.NotesFragment;
import com.example.ganesh.timework.ui.RoutineFragment;
import com.example.ganesh.timework.ui.TasksFragment;
import com.example.ganesh.timework.ui.WeekdayFragment;

public class LandingPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WeekdayFragment.OnWeekdayFragmentInteractionListener,
        RoutineFragment.OnRoutineFragmentInteractionListener,
        NotesFragment.OnNotesFragmentInteractionListener,
TasksFragment.OnTasksFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

//        Toolbar implementation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_landing_page);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

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
        navigationView.getMenu().getItem(0).setChecked(true);

        getSupportFragmentManager().beginTransaction().add(R.id.container_landing_page, NotesFragment.newInstance()).commit();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container_landing_page, NotesFragment.newInstance()).commit();
                getSupportActionBar().setTitle( "Notes" );
                break;
            }

            case R.id.nav_routine: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_landing_page, RoutineFragment.newInstance()).addToBackStack(null).commit();
                getSupportActionBar().setTitle("Routines");
                break;
            }

            case R.id.nav_tasks: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_landing_page, TasksFragment.newInstance()).addToBackStack(null).commit();
                getSupportActionBar().setTitle("Tasks");
                break;
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
