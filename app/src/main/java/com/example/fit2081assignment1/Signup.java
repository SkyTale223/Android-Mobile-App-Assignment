package com.example.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Signup extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }



    public void onButtonRegister(View view){
        String usernameString = etUsername.getText().toString();
        String passwordString = etPassword.getText().toString();
        String confirmPasswordString = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(usernameString) || TextUtils.isEmpty(passwordString) || TextUtils.isEmpty(confirmPasswordString)){
            Toast.makeText(this, "Invalid username or password",Toast.LENGTH_SHORT).show();
        }

        else if(passwordString.equals(confirmPasswordString)){
            Toast.makeText(this, "User successfully registered", Toast.LENGTH_SHORT).show();
            saveUserInformationToSharedPreference(usernameString, passwordString);

            Intent registerIntent = new Intent(this, Login.class);
            startActivity(registerIntent);
        }

        else{
            Toast.makeText(this,"Passwords are different", Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonLogin(View view){
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }


    private void saveUserInformationToSharedPreference(String usernameValue, String passwordValue) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveUserInformationToSharedPreference.edit();
        editor.putString("USER_USERNAME", usernameValue);
        editor.putString("USER_PASSWORD", passwordValue);
        editor.apply();
    }





}
