package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ListEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.app_bar_layout_event);
        getSupportFragmentManager().beginTransaction().replace(R.id.host_container_event, new FragmentListEvent()).commit();

        Toolbar eventToolbar = findViewById(R.id.eventToolbar);
        setSupportActionBar(eventToolbar);
        // Setting the title
        getSupportActionBar().setTitle("All Events");

        // Retrieve the action support bar,setting an "up" back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View back) {
                Context context = ListEventActivity.this;
                Intent Dashboard = new Intent(context, Dashboard.class);
                startActivity(Dashboard);
            }
        });


    }
}
