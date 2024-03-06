package com.example.fit2081assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "TEST", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        Toast.makeText(this,"TEST", Toast.LENGTH_SHORT).show();
    }

    private void upDate2(View view){

    }
}



