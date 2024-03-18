package com.example.fit2081assignment1;


import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    // Stating variables
    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        // Finding the view to find what user has inputted
        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }


    public void onButtonRegister(View view) {
        // Getting user input and converting it to string
        String usernameString = etUsername.getText().toString();
        String passwordString = etPassword.getText().toString();
        String confirmPasswordString = etConfirmPassword.getText().toString();

        // Handling to see if there are any empty inputs
        if (TextUtils.isEmpty(usernameString) || TextUtils.isEmpty(passwordString) || TextUtils.isEmpty(confirmPasswordString)) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        // Handling to see if password is matched
        else if (passwordString.equals(confirmPasswordString)) {
            Toast.makeText(this, "User successfully registered", Toast.LENGTH_SHORT).show();
            saveUserInformationToSharedPreference(usernameString, passwordString);

            Intent registerIntent = new Intent(this, Login.class);
            startActivity(registerIntent);
        }

        // Giving a toast if passwords are different
        else {
            Toast.makeText(this, "Passwords are different", Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonLogin(View view) {
        // Start the next activity of login
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }


    // Creation of shared preferences to user information
    private void saveUserInformationToSharedPreference(String usernameValue, String passwordValue) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveUserInformationToSharedPreference.edit();
        editor.putString("USER_USERNAME", usernameValue);
        editor.putString("USER_PASSWORD", passwordValue);
        editor.apply();
    }


}
