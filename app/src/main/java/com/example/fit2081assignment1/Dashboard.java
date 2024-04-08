package com.example.fit2081assignment1;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.drawer_layout_dashboard); // Use the layout containing the DrawerLayout

        // Find the Toolbar
        Toolbar dashBoardToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(dashBoardToolbar);

        // Find the DrawerLayout
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        // Set up ActionBarDrawerToggle to link the DrawerLayout with the Toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, dashBoardToolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Find the NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Set navigation item selected listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item selection here
                switch (item.getItemId()) {
                    // Handle navigation item clicks
                }
                return true;
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu_items_dashboard, menu);
        return true;
    }

    // When button is clicked it starts the new category intent
    public void onNewEventCategory(View view) {
        Intent newEventCategoryIntent = new Intent(this, NewEventCategory.class);
        startActivity(newEventCategoryIntent);
    }


    // When button is clicked it starts the new event intent
    public void onNewEvent(View view) {
        Intent newEventIntent = new Intent(this, NewEvent.class);
        startActivity(newEventIntent);
    }


}