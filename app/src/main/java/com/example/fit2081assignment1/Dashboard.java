package com.example.fit2081assignment1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.drawer_layout_dashboard);

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