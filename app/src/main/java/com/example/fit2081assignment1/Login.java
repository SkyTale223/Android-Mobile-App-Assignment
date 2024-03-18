package com.example.fit2081assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    EditText tvLoginUsername;
    EditText tvLoginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        tvLoginUsername = findViewById(R.id.editTextLoginUsername);
        tvLoginPassword = findViewById(R.id.editTextLoginPassword);
    }

    public void onButtonLogin(View view){
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);

        String stringLogiuserName = tvLoginUsername.getText().toString();
        String stringLoginPassword = tvLoginPassword.getText().toString();
        String restoredUsername = saveUserInformationToSharedPreference.getString("USER_USERNAME", null);
        String restoredPassword = saveUserInformationToSharedPreference.getString("USER_PASSWORD", null);

        if(stringLogiuserName.equals(restoredUsername) && stringLoginPassword.equals(restoredPassword)){
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent dashboard = new Intent(this, Dashboard.class);
            startActivity(dashboard);
        }

        else{
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }



    }


}