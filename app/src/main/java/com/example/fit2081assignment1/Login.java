package com.example.fit2081assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    // Declaring variables
    EditText etLoginUsername;
    EditText etLoginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // Finding the view to find what user has inputted
        etLoginUsername = findViewById(R.id.editTextLoginUsername);
        etLoginPassword = findViewById(R.id.editTextLoginPassword);
    }


    // Define what the login button does
    public void onButtonLogin(View view) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);

        // Creating both current string of user input, and restoring string of previous user registration
        String stringLoginuserName = etLoginUsername.getText().toString();
        String stringLoginPassword = etLoginPassword.getText().toString();
        String restoredUsername = saveUserInformationToSharedPreference.getString("USER_USERNAME", null);
        String restoredPassword = saveUserInformationToSharedPreference.getString("USER_PASSWORD", null);

        // Validating if current input is the same as the input from the user in the registration page
        if (stringLoginuserName.equals(restoredUsername) && stringLoginPassword.equals(restoredPassword)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent dashboardIntent = new Intent(this, Dashboard.class);
            startActivity(dashboardIntent);
        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }

    // Button register to go back to register page if user wants to do so
    public void onButtonRegister(View view) {
        Intent registerIntent = new Intent(this, Signup.class);
        startActivity(registerIntent);
    }


}