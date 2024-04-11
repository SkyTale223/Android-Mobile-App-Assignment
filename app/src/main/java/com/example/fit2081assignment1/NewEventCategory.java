package com.example.fit2081assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

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
    Switch swIsActive;
    ArrayList<EventCategory> eventCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        // Finding the views
        etCategoryID = findViewById(R.id.editTextCategoryID);
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etEventCount = findViewById(R.id.editTextEventCount);
        swIsActive = findViewById(R.id.switchIsActiveCategory);

        // Initialize the list
        eventCategoryList = new ArrayList<>();
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

        // Log the values being saved
        Log.d("NewEventCategory", "Category ID: " + categoryID.toString());
        Log.d("NewEventCategory", "Category Name: " + etCategoryName.getText().toString());
        Log.d("NewEventCategory", "Event Count: " + etEventCount.getText().toString());
        Log.d("NewEventCategory", "Is Active: " + swIsActive.isChecked());

        // Creating a new EventCategory object
        EventCategory newEventCategory = new EventCategory(
                etCategoryID.getText().toString(),
                etCategoryName.getText().toString(),
                etEventCount.getText().toString(),
                String.valueOf(swIsActive.isChecked())
        );

        Log.d("NewEventCategory", "EventCategory Object: " + newEventCategory.toString());


        // Adding the new EventCategory object to the list
        eventCategoryList.add(newEventCategory);

        // Save the updated list to SharedPreferences
        saveCategoryToSP();
    }

    private void saveCategoryToSP() {
        // Convert the list to JSON string using Gson
        Gson gson = new Gson();
        String json = gson.toJson(eventCategoryList);
        Log.d("JSON_STRING", json);

        // Save the JSON string to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("event_key", json);
        Log.d("SAVE_TO_SHARED_PREF", "Saving changes to SharedPreferences...");
        editor.apply();
        Log.d("SAVED_TO_SP", "Changes saved to SharedPreferences: " );



    }
}
