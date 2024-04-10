package com.example.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Random;
import java.util.StringTokenizer;


public class Dashboard extends AppCompatActivity {

    // Declaring old variables for assignment 1
    EditText etEventID;
    EditText etEventName;
    EditText etCategory;
    EditText etTickets;
    Switch swEventIsActive;

    // Declaring new variables for assignment 2
    Toolbar dashBoardToolbar;
    DrawerLayout dashboardDrawerLayout;
    NavigationView dashboardNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Using drawer layout due to the hierarchy
        setContentView(R.layout.drawer_layout_dashboard);

        // All previous code from new event
        etEventID = findViewById(R.id.editTextEventID);
        etEventName = findViewById(R.id.editTextEventName);
        etCategory = findViewById(R.id.editTextCategory);
        etTickets = findViewById(R.id.editTextTickets);
        swEventIsActive = findViewById(R.id.switchIsActiveEvent);

        // Find the Toolbar
        dashBoardToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(dashBoardToolbar);

        // Find the DrawerLayout
        dashboardDrawerLayout = findViewById(R.id.drawer_layout);

        // Set up ActionBarDrawerToggle to link drawerlayout with toolbar, and also ensuring opening and closing.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dashboardDrawerLayout, dashBoardToolbar, R.string.nav_open, R.string.nav_close);
        // Activate the drawer listener
        dashboardDrawerLayout.addDrawerListener(toggle);
        // Toggle the sync state of the drawer
        toggle.syncState();


        // Find the navigation view
        dashboardNavigationView = findViewById(R.id.nav_view);

        // Handle the navigation, which items are selected
        DashboardNavigationHandler dashboardNavigationHandler = new DashboardNavigationHandler();
        dashboardNavigationView.setNavigationItemSelectedListener(dashboardNavigationHandler);

    }

    // Start of the navigation for the dashboard
    class DashboardNavigationHandler implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        // Upon selecting item, using a if case and comparing id values
        public boolean onNavigationItemSelected(@NonNull MenuItem navitem) {
            int navID = navitem.getItemId();

            if (navID == R.id.view_all_categories) {

            } else if (navID == R.id.add_category) {

                // Manually defining context, as we are in handler class, not the dashboard. Therefore, unable to initalise.
                Context dashboardContext = dashboardDrawerLayout.getContext();
                Intent newEventCategoryIntent = new Intent(dashboardContext, NewEventCategory.class);
                startActivity(newEventCategoryIntent);

            } else if (navID == R.id.view_all_categories) {

            } else if (navID == R.id.logout) {

            }
            dashboardDrawerLayout.closeDrawers();
            return true;
        }
    }


    // Start of the side menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflating the menu (three dots one)
        getMenuInflater().inflate(R.menu.options_menu_items_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem sideMenuItems) {
        int menuID = sideMenuItems.getItemId();
        if (menuID == R.id.refresh) {

        } else if (menuID == R.id.clear_event_form) {

        } else if (menuID == R.id.delete_all_categories) {

        } else if (menuID == R.id.delete_all_events) {

        }
        return true;
    }


    // Method to generate random event ID
    private String generateEventID() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder eventID = new StringBuilder("E");

        // Loop to start appending random characters to the string
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            eventID.append(randomChar);
        }

        // Appending a - for the gap in between
        eventID.append("-");

        // Similarly appending random digits to the string
        for (int i = 0; i < 5; i++) {
            int randomDigit = random.nextInt(10);
            eventID.append(randomDigit);
        }

        return eventID.toString();
    }

    public void onSave(View view) {
        // Calling event ID generator
        etEventID.setText(generateEventID());

        // Check if any field is empty
        if (etEventID.getText().toString().isEmpty() || etEventName.getText().toString().isEmpty() ||
                etCategory.getText().toString().isEmpty() || etTickets.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse ticket count to integer
        try {
            int ticketCount = Integer.parseInt(etTickets.getText().toString());

            if (ticketCount >= 0) {
                boolean eventIsActive = swEventIsActive.isChecked();

                // Save event information to SharedPreferences
                saveEventInformationToSharedPreferences(etEventID.getText().toString(),
                        etEventName.getText().toString(),
                        etCategory.getText().toString(),
                        String.valueOf(ticketCount),
                        String.valueOf(eventIsActive));
            } else {
                Toast.makeText(this, "Ticket count cannot be negative", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Ticket Count", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Event: " + etEventID.getText().toString() + " saved to category " + etCategory, Toast.LENGTH_SHORT).show();
    }

    private void saveEventInformationToSharedPreferences(String eventIDValue, String
            eventNameValue, String eventCategoryValue, String eventTicketValue, String eventActiveValue) {
        SharedPreferences sharedPreferences = getSharedPreferences("EVENT_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EVENT_ID", eventIDValue);
        editor.putString("EVENT_NAME", eventNameValue);
        editor.putString("EVENT_CATEGORY_ID", eventCategoryValue);
        editor.putString("EVENT_TICKET_COUNT", eventTicketValue);
        editor.putString("EVENT_ACTIVE_STATUS", eventActiveValue);
        editor.apply();
    }
}




