package com.example.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;
import java.util.StringTokenizer;

public class NewEventCategory extends AppCompatActivity {

    // Declaring variables
    EditText etCategoryID;
    EditText etCategoryName;
    EditText etEventCount;


    Switch swIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_event_category);

        // Finding the view to find what user has inputted
        etCategoryID = findViewById(R.id.editTextCategoryID);
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etEventCount = findViewById(R.id.editTextEventCount);
        swIsActive = findViewById(R.id.switchIsActiveCategory);

        // Giving permissions to send, receive and read sms messages
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS}, 0);


        // Registering broadcast receiver
        NewEventCategory.CategoryBroadcastReceiver categoryBroadCastReceiver = new NewEventCategory.CategoryBroadcastReceiver();
        registerReceiver(categoryBroadCastReceiver, new IntentFilter(SMSReceiver.CATEGORY_SMS_FILTER), RECEIVER_EXPORTED);


    }

    public void onSave(View view) {
        // Start of the random ID generator
        // Declaring what alphabets and stringbuilder variable
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder categoryID = new StringBuilder();
        Random random = new Random();

        // Appending C to start of ID
        categoryID.append("C");

        // Loop to start appending random characters to the string
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            categoryID.append(randomChar);
        }

        // Appending a - gap in between
        categoryID.append("-");

        // Similarly appending random digits to the string
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);
            categoryID.append(randomDigit);
        }

        // Setting categoryID string on the edit text, starting with C
        etCategoryID.setText(categoryID);

        String categoryString = etCategoryID.getText().toString();
        String categoryNameString = etCategoryName.getText().toString();
        String categoryEventCountString = etEventCount.getText().toString();
        String isActiveString = String.valueOf(swIsActive.isChecked());

        saveCategoryInformationToSharedPreferences(categoryString, categoryNameString, categoryEventCountString, isActiveString);

        Toast.makeText(this, "Category saved successfully: " + categoryID, Toast.LENGTH_SHORT).show();
    }

    // Creating the shared preferences for the category class
    private void saveCategoryInformationToSharedPreferences(String categoryIDValue, String categoryNameValue, String eventCountValue, String categoryActiveValue) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("CATEGORY_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveUserInformationToSharedPreference.edit();
        editor.putString("CATEGORY_ID", categoryIDValue);
        editor.putString("CATEGORY_NAME", categoryNameValue);
        editor.putString("CATEGORY_EVENT_COUNT", eventCountValue);
        editor.putString("CATEGORY_ACTIVE_STATUS", categoryActiveValue);
        editor.apply();
    }

    //Beginning of the broadcast receiver class
    class CategoryBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            // Getting the message from SMSReceiver using Intent
            String incomingMessage = intent.getStringExtra(SMSReceiver.CATEGORY_SMS_MSG_KEY);

            // If the message is not null and starts with "category:" then proceed
            if (incomingMessage != null && incomingMessage.startsWith("category:")) {
                // Remove the "category:" prefix
                incomingMessage = incomingMessage.substring(9);

                // Tokenize the message using ";" as the delimiter
                StringTokenizer tokenizer = new StringTokenizer(incomingMessage, ";");

                // Check if the number of tokens is correct
                if (tokenizer.countTokens() >= 3) {
                    // Extract category details from tokens
                    String categoryName = tokenizer.nextToken();
                    String eventCount = tokenizer.nextToken();
                    String categoryIsActive = tokenizer.nextToken();

                    try {
                        // Parse event count to integer
                        int count = Integer.parseInt(eventCount);

                        // Check if count is non-negative
                        if (count >= 0) {
                            // Convert isActive string to uppercase and parse to boolean
                            categoryIsActive = categoryIsActive.toUpperCase();

                            if (categoryIsActive.equals("TRUE") || categoryIsActive.equals("FALSE")) {
                                boolean active = Boolean.parseBoolean(categoryIsActive);

                                // Update UI with category details
                                etCategoryName.setText(categoryName);
                                etEventCount.setText(String.valueOf(count));
                                swIsActive.setChecked(active);

                                // Show a toast message
                                Toast.makeText(context, "Category updated", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle invalid isActive value
                                Toast.makeText(context, "Invalid Category Active value", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle negative event count
                            Toast.makeText(context, "Event count cannot be negative", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid event input
                        Toast.makeText(context, "Invalid Event Count", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle invalid message format
                    Toast.makeText(context, "Invalid message format", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle unknown or invalid command
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }
        }
    }
}