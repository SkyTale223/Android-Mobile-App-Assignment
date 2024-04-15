package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.app_bar_layout_category);
        getSupportFragmentManager().beginTransaction().replace(R.id.host_container_category, new FragmentListCategory()).commit();

        Toolbar dashboardToolbar = findViewById(R.id.categoryToolbar);
        setSupportActionBar(dashboardToolbar);
        // Setting the title
        getSupportActionBar().setTitle("All Categories");

        // Retrieve the action support bar,setting an "up" back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Listening for when the back button is clicked
        dashboardToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View back) {
                Context context = ListCategoryActivity.this;
                Intent Dashboard = new Intent(context, Dashboard.class);
                startActivity(Dashboard);
            }
        });
    }
}