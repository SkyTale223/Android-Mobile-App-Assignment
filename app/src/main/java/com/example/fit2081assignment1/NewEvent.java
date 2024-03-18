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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
import java.util.StringTokenizer;

public class NewEvent extends AppCompatActivity {

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

        etEventID = findViewById(R.id.editTextEventID);
        etEventName = findViewById(R.id.editTextEventName);
        etCategory = findViewById(R.id.editTextCategory);
        etTickets = findViewById(R.id.editTextTickets);
        swEventIsActive = findViewById(R.id.switchIsActiveEvent);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS}, 0);

        NewEvent.EventBroadCastReceiver myBroadCastReceiver = new NewEvent.EventBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

    }


    public void onSave(View view) {
        //Start of the random ID generator
        //Declaring what alphabets and stringbuilder variable
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder eventID = new StringBuilder();
        Random random = new Random();

        //Appending C to start of ID
        eventID.append("E");

        //Loop to start appending random characters to the string
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            eventID.append(randomChar);
        }

        //Appending a - gap in between
        eventID.append("-");

        //Similarly appending random digits to the string
        for (int i = 0; i < 4; i++) {
            int randomDigit = random.nextInt(10);
            eventID.append(randomDigit);
        }

        //Setting categoryID string on the edit text, starting with C
        etEventID.setText(eventID);

    }

    class EventBroadCastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            // Getting the message from SMSReceiver using Intent
            String incomingMessage = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);


            // If the message is null or doesn't start with "event:", exit the method
            if (incomingMessage == null || !incomingMessage.startsWith("event:")) {
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
                return;
            }

            // Replace the first 6 letters ("event:") as they are not needed
            incomingMessage = incomingMessage.substring(6);

            // Tokenize the message with ";" as delimiter
            StringTokenizer sTEvent = new StringTokenizer(incomingMessage, ";");

            // Check the number of tokens in the message
            if (sTEvent.countTokens() >= 4) {
                // Extract event information from tokens
                String eventNameString = sTEvent.nextToken();
                String categoryIDString = sTEvent.nextToken();
                String ticketsAvailableString = sTEvent.nextToken();
                String isActiveEventString = sTEvent.nextToken();

                // Parsing ticket count and isActive values
                int eventCount;
                boolean isActive;
                try {
                    eventCount = Integer.parseInt(ticketsAvailableString);
                    isActive = Boolean.parseBoolean(isActiveEventString.toUpperCase());
                } catch (NumberFormatException e) {
                    // Handling invalid ticket count
                    Toast.makeText(context, "Invalid Ticket Count", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences saveCategoryInformationToSharedPreferences = getSharedPreferences("CATEGORY_INFORMATION", MODE_PRIVATE);

                // Check if the previous SharedPreferences ID matches the new event ID
                String restoredID = saveCategoryInformationToSharedPreferences.getString("CATEGORY_ID", null);
                if (restoredID != null && restoredID.equals(categoryIDString)) {
                    // IDs match, update the UI with event information
                    etEventName.setText(eventNameString);
                    etTickets.setText(String.valueOf(eventCount));
                    swEventIsActive.setChecked(isActive);
                    Toast.makeText(context, "Event updated", Toast.LENGTH_SHORT).show();
                } else {
                    // IDs don't match, display an error message
                    Toast.makeText(context, "Previous category ID does not match the new event ID.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // If the message format is invalid, display an error message
                Toast.makeText(context, "Invalid message format", Toast.LENGTH_SHORT).show();
            }
        }

    }
}



