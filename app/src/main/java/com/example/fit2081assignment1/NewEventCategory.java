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

    //Declaring global variables
    EditText etCategoryID;
    EditText etCategoryName;
    EditText etEventCount;

    Switch swIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_event_category);

        //Setting the view to find what user has inputted
        etCategoryID = findViewById(R.id.editTextCategoryID);
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etEventCount = findViewById(R.id.editTextEventCount);
        swIsActive = findViewById(R.id.switchIsActive);

        //Giving permissions to send, receive and read sms messages
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS}, 0);


        //Registering broadcast receiver
        NewEventCategory.CategoryBroadcastReceiver myBroadCastReceiver = new NewEventCategory.CategoryBroadcastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);


    }

    public void onSave(View view) {
        //Start of the random ID generator
        //Declaring what alphabets and stringbuilder variable
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder categoryID = new StringBuilder();
        Random random = new Random();

        //Appending C to start of ID
        categoryID.append("C");

        //Loop to start appending random characters to the string
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            categoryID.append(randomChar);
        }

        //Appending a - gap in between
        categoryID.append("-");

        //Similarly appending random digits to the string
        for (int i = 0; i < 3; i++) {
            int randomDigit = random.nextInt(10);
            categoryID.append(randomDigit);
        }

        //Setting categoryID string on the edit text, starting with C
        etCategoryID.setText(categoryID);

        String categoryString = etCategoryID.getText().toString();
        String categoryNameString = etCategoryName.getText().toString();
        String categoryEventCountString = etEventCount.getText().toString();
        String isActiveString = String.valueOf(swIsActive.isChecked());

        saveCategoryInformationToSharedPreferences(categoryString, categoryNameString, categoryEventCountString, isActiveString);

        Toast.makeText(this, "Category saved successfully: " + categoryID, Toast.LENGTH_SHORT).show();
    }


    private void saveCategoryInformationToSharedPreferences(String categoryIDValue, String categoryNameValue, String eventCountValue, String isActiveValue) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("CATEGORY_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveUserInformationToSharedPreference.edit();
        editor.putString("CATEGORY_ID", categoryIDValue);
        editor.putString("CATEGORY_NAME_", categoryNameValue);
        editor.putString("CATEGORY_EVENT_COUNT", eventCountValue);
        editor.putString("IS_ACTIVE_STATUS", isActiveValue);
        editor.apply();
    }

    //Beginning fo the broadcast receiver class
    class CategoryBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            //Getting the message from SMSReceiver using Intent
            String incomingMessage = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            //If the message is null and it start with category then proceed
            if (incomingMessage != null && incomingMessage.startsWith("category:")) {
                //Replace the first 8 letters (category) as it will not need to be appended
                incomingMessage = incomingMessage.substring(9);

                //Telling string tokenizer that the delimiter character is ;
                StringTokenizer sTCategory = new StringTokenizer(incomingMessage, ";");
                //Check amount of tokens in the message
                if (sTCategory.countTokens() >= 3) {
                    String categoryNameString = sTCategory.nextToken();
                    String eventCountString = sTCategory.nextToken();
                    String isActiveString = sTCategory.nextToken();

                    //Start of the try and catch to debug the messages the user sent, checking for any invalid bits
                    try {
                        //Check eventcount is actually an integer by parsing it from a string to int
                        int eventCount = Integer.parseInt(eventCountString);

                        //Changing boolean checking to all uppercase, due to case sensitivity
                        isActiveString = isActiveString.toUpperCase();

                        //If the active string is equal to either true or false then it will proceed
                        if (isActiveString.equals("TRUE") || isActiveString.equals("FALSE")) {
                            boolean isActive = Boolean.parseBoolean(isActiveString);
                            etCategoryName.setText(categoryNameString);
                            etEventCount.setText(String.valueOf(eventCount));
                            swIsActive.setChecked(isActive);
                            Toast.makeText(context, "Category updated", Toast.LENGTH_SHORT).show();
                        } else {
                            //Bunch of error messages
                            Toast.makeText(context, "Incorrect Event Active State", Toast.LENGTH_SHORT).show();
                        }
                        //Catching if the number is a negative then it will give a certain error message
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Invalid Event Count", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Invalid message format", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

