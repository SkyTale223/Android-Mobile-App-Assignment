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

    EditText tvUsername;
    EditText tvPassword;
    EditText tvConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        tvUsername = findViewById(R.id.editTextUsername);
        tvPassword = findViewById(R.id.editTextPassword);
        tvConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }



    public void onButtonRegister(View view){
        String usernameString = tvUsername.getText().toString();
        String passwordString = tvPassword.getText().toString();
        String confirmPasswordString = tvConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(usernameString) || TextUtils.isEmpty(passwordString) || TextUtils.isEmpty(confirmPasswordString)){
            Toast.makeText(this, "Invalid username or password",Toast.LENGTH_SHORT).show();
        }

        else if(passwordString.equals(confirmPasswordString)){
            Toast.makeText(this, "User successfully registered", Toast.LENGTH_SHORT).show();
            saveUserInformationToSharedPreference(usernameString, passwordString);

            Intent loginPage = new Intent(this, Login.class);
            loginPage.putExtra("Username", usernameString);
            loginPage.putExtra("Password", passwordString);
            startActivity(loginPage);
        }

        else{
            Toast.makeText(this,"Passwords are different", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveUserInformationToSharedPreference(String usernameValue, String passwordValue) {
        SharedPreferences saveUserInformationToSharedPreference = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveUserInformationToSharedPreference.edit();
        editor.putString("USER_USERNAME", usernameValue);
        editor.putString("USER_PASSWORD", passwordValue);
        editor.apply();
    }





}
