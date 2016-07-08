package com.example.ganesh.timework;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ganesh.timework.adapter.TaskRecycleAdapter;
import com.example.ganesh.timework.data.ListItemTemp;

public class TaskPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tasks");
        getSupportActionBar().setHomeButtonEnabled(true);

        ListItemTemp.TaskItem[] mTaskItemArray = new ListItemTemp.TaskItem[]{
                new ListItemTemp.TaskItem(1 , "Get a Continental GT" , "0" , "Aug 18" , ListItemTemp.TaskItem.type.PERSONAL , true) ,
                new ListItemTemp.TaskItem(1 , "Get a Macbook pro" , "0" , "Aug 18" , ListItemTemp.TaskItem.type.PERSONAL , true)
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskPageActivity.this));
        recyclerView.setAdapter(new TaskRecycleAdapter(mTaskItemArray));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
