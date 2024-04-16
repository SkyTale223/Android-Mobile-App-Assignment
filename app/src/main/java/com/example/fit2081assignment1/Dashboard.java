package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Dashboard extends AppCompatActivity {

    EditText etEventID;
    EditText etEventName;
    EditText etCategory;
    EditText etTickets;
    Switch swEventIsActive;
    FloatingActionButton dashboardFab;
    DrawerLayout dashboardDrawerLayout;

    EventEvent newEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_dashboard);

        etEventID = findViewById(R.id.editTextEventID);
        etEventName = findViewById(R.id.editTextEventName);
        etCategory = findViewById(R.id.editTextCategory);
        etTickets = findViewById(R.id.editTextTickets);
        swEventIsActive = findViewById(R.id.switchIsActiveEvent);

        Toolbar dashboardToolbar = findViewById(R.id.dashboardToolbar);
        setSupportActionBar(dashboardToolbar);
        // Setting the title
        getSupportActionBar().setTitle("33162050 Assignment 2");


        dashboardFab = findViewById(R.id.fab);
        dashboardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View FAB) {
                saveEvent();
            }
        });

        dashboardDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dashboardDrawerLayout, dashboardToolbar, R.string.nav_open, R.string.nav_close);
        dashboardDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView dashboardNavigationView = findViewById(R.id.nav_view);
        dashboardNavigationView.setNavigationItemSelectedListener(new DashboardNavigationHandler());


        // Initalise the fragment on startup
        refreshFragmentCategory();
    }

    // Start the navigation drawer
    class DashboardNavigationHandler implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int navID = menuItem.getItemId();
            if (navID == R.id.view_all_categories) {
                Context context = Dashboard.this;
                Intent viewCategoryIntent = new Intent(context, ListCategoryActivity.class);
                startActivity(viewCategoryIntent);
            } else if (navID == R.id.add_category) {
                Context context = Dashboard.this;
                Intent viewEventIntent = new Intent(context, NewEventCategory.class);
                startActivity(viewEventIntent);

            }else if (navID == R.id.view_all_events){
                Context context = Dashboard.this;
                Intent viewEventIntent = new Intent(context, ListEventActivity.class);
                startActivity(viewEventIntent);
            } else if (navID == R.id.logout) {
                logout();
            }
            dashboardDrawerLayout.closeDrawers();
            return true;
        }


    }

    // Inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_items_dashboard, menu);
        return true;
    }

    // Determines what options items are selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int menuID = menuItem.getItemId();
        if (menuID == R.id.refresh) {
            refreshFragmentCategory();
        } else if (menuID == R.id.clear_event_form) {
            clearEventForm();
        } else if (menuID == R.id.delete_all_categories) {
            deleteAllCategories();
        } else if (menuID == R.id.delete_all_events) {
            deleteAllEvents();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    // Clear the event form
    private void clearEventForm() {
        etEventID.setText("");
        etCategory.setText("");
        etEventName.setText("");
        etTickets.setText("");
        swEventIsActive.setChecked(false);
    }

    // Delete all categories
    private void deleteAllCategories() {
        SharedPreferences deleteCategoryPrefrences = getSharedPreferences("spCategory", MODE_PRIVATE);
        SharedPreferences.Editor deleteEditor = deleteCategoryPrefrences.edit();
        // Remove the key
        deleteEditor.remove("keyCategory");
        deleteEditor.apply();
        refreshFragmentCategory();
    }

    // Delete all events
    private void deleteAllEvents() {
        SharedPreferences deleteEventPrefrences = getSharedPreferences("spEvent", MODE_PRIVATE);
        SharedPreferences.Editor deleteEditor = deleteEventPrefrences.edit();
        // Remove the key
        deleteEditor.remove("keyEvent");
        deleteEditor.apply();
    }

    // Refreshes the category fragment so it displays new fragments
    private void refreshFragmentCategory() {
        getSupportFragmentManager().beginTransaction().replace(R.id.host_container_dashboard, new FragmentListCategory()).commit();
    }

    private void logout(){
        Intent login = new Intent(this, Login.class);
        startActivity(login);
        finish();
    }



    private void saveEvent() {
        // Start of the random ID generator
        //Declaring what alphabets and stringbuilder variable
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder eventID = new StringBuilder();
        Random random = new Random();

        // Appending E to start of ID
        eventID.append("E");

        // Loop to start appending random characters to the string
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

        // Setting categoryID string on the edit text, starting with C
        etEventID.setText(eventID);

        saveEventInformation();




        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Event saved", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Undo the last saved event
                undo();
            }
        });
        snackbar.show();
    }




    private void saveEventInformation() {
        // Retrieve event information from EditText and Switch
        String strEventID = etEventID.getText().toString();
        String strEventName = etEventName.getText().toString();
        String strEventCategoryID = etCategory.getText().toString();
        String strTicketCount = etTickets.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("spCategory", MODE_PRIVATE);
        Gson gson = new Gson();
        String existingCategoryDataJson = sharedPreferences.getString("keyCategory", "[]");
        Type typeCategory = new TypeToken<ArrayList<EventCategory>>() {
        }.getType();
        ArrayList<EventCategory> currentCategoryData = gson.fromJson(existingCategoryDataJson, typeCategory);



        if (strEventName.isEmpty() || strEventCategoryID.isEmpty() || strTicketCount.isEmpty()) {
            // Show error message if it is empty
            Toast.makeText(this, "Please ensure all inputs are filled out and valid", Toast.LENGTH_SHORT).show();
            clearEventForm();
            return;
        }

        int intTicketCount = Integer.parseInt(strTicketCount);
        if (intTicketCount <= 0) {
            // Show error message or handle negative or zero input
            Toast.makeText(this, "Ticket count must be a positive integer", Toast.LENGTH_SHORT).show();
            clearEventForm();
            return;
        }

        boolean hasAlphabets = false;
        // Iterating over each character and comparing them with the built in char in java
        for (char c : strEventName.toCharArray()){
            if (Character.isLetter(c)){
                hasAlphabets = true;
                break;
            }
        }

        if (!hasAlphabets){
            Toast.makeText(this, "Event Name must contain alphabets", Toast.LENGTH_SHORT).show();
            clearEventForm();
            return;
        }

        boolean isValidCategoryID = false;
        // Using a for loop, cycle for all category in the array list
        for (EventCategory category : currentCategoryData) {
            Log.d("EventCategory", "Category ID from list: " + category.getCategoryID());
            Log.d("EventCategory", "Event Category ID from input: " + strEventCategoryID);

            // If this is equal to then append data to new array list
            if (strEventCategoryID.equals(category.getCategoryID())) {
                // Category ID is valid, save event information and exit loop
                isValidCategoryID = true;
                newEvent = new EventEvent(
                        strEventID,
                        strEventName,
                        strEventCategoryID,
                        intTicketCount,
                        swEventIsActive.isChecked()
                );

                int currentEventCount = category.getCategoryEventCount();
                category.setCategoryEventCount(currentEventCount + 1);
                saveEventInformationToSharedPreferences(newEvent);
                // Breaking out of loop upon valid category
                Toast.makeText(this, "Event Saved: " + strEventID + " to " + category.getCategoryID()  ,Toast.LENGTH_SHORT).show();
                break;
            }
        }

        String updatedCategoryDataJson = gson.toJson(currentCategoryData);
        sharedPreferences.edit().putString("keyCategory", updatedCategoryDataJson).apply();

        if (!isValidCategoryID) {
            // If no matching category ID is found, display error message
            clearEventForm();
            Toast.makeText(this, "Category ID invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void undo() {
        if (newEvent != null){
            SharedPreferences eventSharedPreferences = getSharedPreferences("spEvent", MODE_PRIVATE);
            Gson gson = new Gson();

            // Retrieve existing data from SharedPreferences
            String existingEventDataJson = eventSharedPreferences.getString("keyEvent", "[]");

            Type typeEvent = new TypeToken<ArrayList<EventEvent>>() {}.getType();
            ArrayList<EventEvent> existingEventData = gson.fromJson(existingEventDataJson, typeEvent);

            if (existingEventData != null) {
                // Remove the last saved event from the list
                existingEventData.remove(newEvent);

                String updatedEventDataJson = gson.toJson(existingEventData);

                // Save the updated data back to SharedPreferences
                SharedPreferences.Editor editor = eventSharedPreferences.edit();
                editor.putString("keyEvent", updatedEventDataJson);
                editor.apply();

                // Notify the user that the event has been undone
                Toast.makeText(this, "Undone last saved event", Toast.LENGTH_SHORT).show();

                // Clear the fields to reflect the undone event
                clearEventForm();
            }
        }
    }





    private void saveEventInformationToSharedPreferences(EventEvent newEvent) {
        // Save event information to SharedPreferences
        SharedPreferences eventSharedPreferences = getSharedPreferences("spEvent", MODE_PRIVATE);

        Gson gson = new Gson();

        // Retrieve existing data from SharedPreferences
        String existingEventDataJson = eventSharedPreferences.getString("keyEvent", "[]");

        Type typeEvent = new TypeToken<ArrayList<EventEvent>>() {
        }.getType();
        ArrayList<EventEvent> existingEventData = gson.fromJson(existingEventDataJson, typeEvent);

        // Append the new data to the existing data
        if (existingEventData == null) {
            existingEventData = new ArrayList<>();
        }
        existingEventData.add(newEvent);

        // Convert the updated data to JSON
        String updatedEventDataJson = gson.toJson(existingEventData);

        Log.d("SharedPreferences", "Updated Event Data JSON: " + updatedEventDataJson);


        // Save the updated data back to SharedPreferences
        SharedPreferences.Editor editor = eventSharedPreferences.edit();
        editor.putString("keyEvent", updatedEventDataJson);
        editor.apply();
    }


}

