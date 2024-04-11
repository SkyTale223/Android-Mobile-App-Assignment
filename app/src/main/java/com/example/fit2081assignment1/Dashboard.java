package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import java.util.Random;

public class Dashboard extends AppCompatActivity {

    EditText etEventID;
    EditText etEventName;
    EditText etCategory;
    EditText etTickets;
    Switch swEventIsActive;

    DrawerLayout dashboardDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_dashboard);

        etEventID = findViewById(R.id.editTextEventID);
        etEventName = findViewById(R.id.editTextEventName);
        etCategory = findViewById(R.id.editTextCategory);
        etTickets = findViewById(R.id.editTextTickets);
        swEventIsActive = findViewById(R.id.switchIsActiveEvent);

        Toolbar dashboardToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(dashboardToolbar);

        dashboardDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dashboardDrawerLayout, dashboardToolbar, R.string.nav_open, R.string.nav_close);
        dashboardDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView dashboardNavigationView = findViewById(R.id.nav_view);
        dashboardNavigationView.setNavigationItemSelectedListener(new DashboardNavigationHandler());
    }

    class DashboardNavigationHandler implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int navID = menuItem.getItemId();
            if (navID == R.id.view_all_categories) {
                // Handle view all categories
            } else if (navID == R.id.add_category) {
                Context context = Dashboard.this;
                Intent newEventCategoryIntent = new Intent(context, NewEventCategory.class);
                startActivity(newEventCategoryIntent);
            } else if (navID == R.id.logout) {
                // Handle logout
            }
            dashboardDrawerLayout.closeDrawers();
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_items_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int menuID = menuItem.getItemId();
        if (menuID == R.id.refresh) {
            FragmentListCategory fragment = (FragmentListCategory) getSupportFragmentManager().findFragmentByTag("fragment_list_category_tag");
            if (fragment != null) {
                // Refresh the fragment by calling its refresh method
                fragment.refresh();
            }
        } else if (menuID == R.id.clear_event_form) {
            clearEventForm();
        } else if (menuID == R.id.delete_all_categories) {
            deleteAllCategories();
        } else if (menuID == R.id.delete_all_events) {
            deleteAllEvents();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void clearEventForm() {
        // Clear event form code
        etEventID.setText("");
        etEventName.setText("");
        etCategory.setText("");
        etTickets.setText("");
        swEventIsActive.setChecked(false);
    }

    private void deleteAllCategories() {
        // Delete all categories code
    }

    private void deleteAllEvents() {
        // Delete all events code
    }

    private String generateEventID() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder eventID = new StringBuilder("E");
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            eventID.append(randomChar);
        }
        eventID.append("-");
        for (int i = 0; i < 5; i++) {
            int randomDigit = random.nextInt(10);
            eventID.append(randomDigit);
        }
        return eventID.toString();
    }

    public void onSave(View view) {
        etEventID.setText(generateEventID());
        if (etEventID.getText().toString().isEmpty() || etEventName.getText().toString().isEmpty() ||
                etCategory.getText().toString().isEmpty() || etTickets.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int ticketCount = Integer.parseInt(etTickets.getText().toString());
            if (ticketCount >= 0) {
                boolean eventIsActive = swEventIsActive.isChecked();
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
