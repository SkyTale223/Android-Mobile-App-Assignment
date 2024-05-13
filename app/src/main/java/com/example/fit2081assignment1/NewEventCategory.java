package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class NewEventCategory extends AppCompatActivity {

    // Declaring variables
    EditText etCategoryID;
    EditText etCategoryName;
    EditText etEventCount;
    EditText etEventLocation;
    Switch swIsActive;
    ArrayList<EventCategory> eventCategoryList;

    private EMAViewmodel emaViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        // Finding the views
        etCategoryID = findViewById(R.id.editTextCategoryID);
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etEventCount = findViewById(R.id.editTextEventCount);
        swIsActive = findViewById(R.id.switchIsActiveCategory);
        etEventLocation = findViewById(R.id.editTextEventLocation);


        // Initialize the list
        eventCategoryList = new ArrayList<>();
        emaViewModel = new ViewModelProvider(this).get(EMAViewmodel.class);
    }

    public void onSave(View view) {
        // Start of the random ID generator
        // Declaring what alphabets and StringBuilder variable
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder categoryID = new StringBuilder();
        Random random = new Random();

        // Appending "C" to start of ID
        categoryID.append("C");

        // Loop to start appending random characters to the string
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            categoryID.append(randomChar);
        }

        // Appending a "-" gap in between
        categoryID.append("-");

        // Similarly appending random digits to the string
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);
            categoryID.append(randomDigit);
        }

        // Setting categoryID string on the edit text, starting with "C"
        etCategoryID.setText(categoryID);

        /* Debugging
        Log.d("NewEventCategory", "Category ID: " + categoryID.toString());
        Log.d("NewEventCategory", "Category Name: " + etCategoryName.getText().toString());
        Log.d("NewEventCategory", "Event Count: " + etEventCount.getText().toString());
        Log.d("NewEventCategory", "Is Active: " + swIsActive.isChecked());
         */

        String strCategoryID = etCategoryID.getText().toString();
        String strCategoryName = etCategoryName.getText().toString();
        String strEventCount = etEventCount.getText().toString();
        String strEventLocation = etEventLocation.getText().toString();

        if (strCategoryName.isEmpty() || strEventCount.isEmpty()) {
            // Show error message if it is empty
            Toast.makeText(this, "Please ensure all inputs are filled out and valid", Toast.LENGTH_SHORT).show();
            clearCategoryInput();
            return;
        }

        // Start off by declaring a boolean to determine if alphabets are present
        boolean hasAlphabets = false;
        // Iterating over each character and comparing them with the built in char in java
        for (char c : strCategoryName.toCharArray()) {
            if (Character.isLetter(c)) {
                hasAlphabets = true;
                break;
            }
        }

        if (!hasAlphabets) {
            Toast.makeText(this, "Category Name must contain alphabets", Toast.LENGTH_SHORT).show();
            clearCategoryInput();
            return;
        }

        int intEventCount = Integer.parseInt(strEventCount);
        if (intEventCount <= 0) {
            // Show error message or handle negative or zero input
            Toast.makeText(this, "Event Count must be a positive integer", Toast.LENGTH_SHORT).show();
            clearCategoryInput();
            return;
        }


        EventCategory newEventCategory = new EventCategory(
                strCategoryID,
                strCategoryName,
                intEventCount,
                swIsActive.isChecked(),
                strEventLocation
        );


        emaViewModel.insert(newEventCategory);
        Toast.makeText(this, "Category saved successfully: " + strCategoryID, Toast.LENGTH_SHORT).show();

        Intent dashboardIntent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(dashboardIntent);
    }

    private void clearCategoryInput() {
        etCategoryID.setText("");
        etCategoryName.setText("");
        etEventCount.setText("");
        swIsActive.setChecked(false);

    }



}

