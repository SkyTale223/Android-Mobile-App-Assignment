package com.example.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import java.util.Random;
import java.util.StringTokenizer;

public class NewEvent extends AppCompatActivity {
    // Declaring variables
    EditText etEventID;
    EditText etEventName;
    EditText etCategory;
    EditText etTickets;
    Switch swEventIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_event);

        // Setting the view to find what user has inputted
        etEventID = findViewById(R.id.editTextEventID);
        etEventName = findViewById(R.id.editTextEventName);
        etCategory = findViewById(R.id.editTextCategory);
        etTickets = findViewById(R.id.editTextTickets);
        swEventIsActive = findViewById(R.id.switchIsActiveEvent);

        // Finding permissions to send, receive and read sms messages
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS}, 0);

        // Registering broadcast receiver
        NewEvent.EventBroadCastReceiver myBroadCastReceiver = new NewEvent.EventBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.EVENT_SMS_FILTER), RECEIVER_EXPORTED);

    }


    public void onSave(View view) {
        // Start of the random ID generator
        //Declaring what alphabets and stringbuilder variable
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder eventID = new StringBuilder();
        Random random = new Random();

        // Appending C to start of ID
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
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);
            eventID.append(randomDigit);
        }

        // Setting categoryID string on the edit text, starting with C
        etEventID.setText(eventID);


        String eventIDString = etEventID.getText().toString();
        String eventNameString = etEventName.getText().toString();
        String eventCategoryID = etCategory.getText().toString();
        String eventTickets = etTickets.getText().toString();
        String isActiveString = String.valueOf(swEventIsActive.isChecked());


        // Saving all information the the shared preference for events
        saveEventInformationToSharedPreferences(eventIDString, eventNameString, eventCategoryID, eventTickets, isActiveString);

        Toast.makeText(this, "Event: " + etEventID.getText().toString() + " saved to category " + eventCategoryID, Toast.LENGTH_SHORT).show();
    }


    // Creating the shared Preferences for Events
    private void saveEventInformationToSharedPreferences(String eventIDValue, String eventNameValue, String eventCategoryValue, String eventTicketValue, String eventActiveValue) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("CATEGORY_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveUserInformationToSharedPreference.edit();
        editor.putString("EVENT_ID", eventIDValue);
        editor.putString("EVENT_NAME", eventNameValue);
        editor.putString("EVENT_CATEGORY_ID", eventCategoryValue);
        editor.putString("EVENT_TICKET_COUNT", eventTicketValue);
        editor.putString("EVENT_ACTIVE_STATUS", eventActiveValue);
        editor.apply();
    }


    // Start of broacast receiver class to listen for messages
    class EventBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Using the unique event msg key due to it being an event message
            String incomingMessage = intent.getStringExtra(SMSReceiver.EVENT_SMS_MSG_KEY);

            /*If the incoming message here is not null then it will subtract the first 6 character "event:"
             * Upon subtracting that it will then tokenize each part into the specific string
             * After that it'll parse the string and begin checking if ticket count is above 0
             * If boolean value is valid and give a toast upon successful updates of everything*/
            if (incomingMessage != null) {
                incomingMessage = incomingMessage.substring(6);

                StringTokenizer sTevent = new StringTokenizer(incomingMessage, ";");
                if (sTevent.countTokens() == 4) {
                    String eventNameString = sTevent.nextToken();
                    String categoryIDString = sTevent.nextToken();
                    String eventTicketString = sTevent.nextToken();
                    String eventIsActiveString = sTevent.nextToken();

                    try {
                        int ticketCount = Integer.parseInt(eventTicketString);

                        // Check if ticketCount is negative
                        if (ticketCount >= 0) {
                            eventIsActiveString = eventIsActiveString.toUpperCase();

                            if (eventIsActiveString.equals("TRUE") || eventIsActiveString.equals("FALSE")) {
                                boolean eventIsActive = Boolean.parseBoolean(eventIsActiveString);
                                SharedPreferences sharedPreferences = context.getSharedPreferences("CATEGORY_INFORMATION", Context.MODE_PRIVATE);
                                String savedCategoryID = sharedPreferences.getString("CATEGORY_ID", "");

                                if (savedCategoryID.equals(categoryIDString)) {
                                    etEventName.setText(eventNameString);
                                    etTickets.setText(String.valueOf(ticketCount));
                                    swEventIsActive.setChecked(eventIsActive);
                                    etCategory.setText(savedCategoryID);
                                } else {
                                    // Handle invalid category ID
                                    Toast.makeText(context, "Category ID does not match", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Handle invalid event active state
                                Toast.makeText(context, "Incorrect Event Active State", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle negative ticket count
                            Toast.makeText(context, "Ticket count cannot be negative", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid ticket input
                        Toast.makeText(context, "Invalid Ticket Count", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unknown or invalid command
                    Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}