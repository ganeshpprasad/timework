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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ganesh.timework.ui.TagsFragment;
import com.example.ganesh.timework.ui.TodayFragment;
import com.example.ganesh.timework.ui.RoutineFragment;
import com.example.ganesh.timework.ui.TasksFragment;

import org.w3c.dom.Text;

/**
 * Landing page contains
 *
 * Drawer implementation
 * NavigationView implementation
 * replacing fragments
 *
 * 1. Notes - still debatable feature. Hoping to make tasks without reminders as Notes
 *
 * 2. The dashboard page. But dashboard page is not designed. Yet. THE DRAWER IS NOT WORKING
 */

public class LandingPageActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    RoutineFragment.OnRoutineFragmentInteractionListener,
    TodayFragment.OnNotesFragmentInteractionListener,
    TasksFragment.OnTasksFragmentInteractionListener {

  private static final String LOG_TAG = LandingPageActivity.class.getSimpleName();

  NavigationView navigationView;
  DrawerLayout drawer;

//    private static final String LOG_TAG = "Landing page";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_landing_page);

//
//        NavigationView implementation
//
    navigationView = (NavigationView) findViewById(R.id.nav_view);
    if (navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);
      navigationView.getMenu().getItem(0).setChecked(true);
    }

//
//        About in navigation view
//
    TextView aboutTv = (TextView) findViewById(R.id.about_landing_page);
    aboutTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LandingPageActivity.this, AboutActivity.class);
        startActivity(intent);
      }
    });

//
//        Load today's fragment
//
    getSupportFragmentManager().beginTransaction()
        .add(R.id.container_landing_page, TodayFragment.newInstance()).commit();

//
//        Toolbar implementation
//
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_landing_page);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Today");

//
//        Drawer implementation
//
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

  }

  @Override
  public void onBackPressed() {
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer != null) {
      if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
      } else {
        super.onBackPressed();
      }
    }else{
      Log.d(LOG_TAG, "onBackPressed: returned null ");
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
//
//     Handle navigation view item clicks here.
//
    int id = item.getItemId();

    switch (id) {

      case R.id.nav_home: {
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_landing_page, TodayFragment.newInstance()).commit();
        getSupportActionBar().setTitle("Today");

        break;
      }

      case R.id.nav_routine: {
        navigationView.getMenu().getItem(1).setChecked(true);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_landing_page, RoutineFragment.newInstance()).commit();
        getSupportActionBar().setTitle("Routines");

        break;
      }

      case R.id.nav_tasks: {
        navigationView.getMenu().getItem(2).setChecked(true);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_landing_page, TasksFragment.newInstance()).commit();
        getSupportActionBar().setTitle("Tasks");

        break;
      }

      case R.id.nav_tags: {
        navigationView.getMenu().getItem(3).setChecked(true);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_landing_page, TagsFragment.newInstance()).commit();
        getSupportActionBar().setTitle("Tags");

        break;
      }
    }

    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer != null) {
      drawer.closeDrawer(GravityCompat.START);
    }
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
