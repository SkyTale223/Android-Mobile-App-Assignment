package com.example.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS}, 0);

        Dashboard.broadcastReceiver myBroadCastReceiver = new Dashboard.broadcastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

    }

    public void onButtonClick(View view){
        Intent test = new Intent(this, NewEvent.class);
        startActivity(test);
    }

    class broadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String incomingMessage = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            if (incomingMessage != null) {
                if (incomingMessage.startsWith("event")) {
                    Intent newEventIntent = new Intent();
                    newEventIntent.setAction("NEW_EVENT_MESSAGE");
                    newEventIntent.putExtra("message", incomingMessage);}

                if (incomingMessage.startsWith("category")) {
                    Intent newEventCategory = new Intent();
                    newEventCategory.setAction("NEW_EVENT_CATEGORY");
                    newEventCategory.putExtra("message", incomingMessage);
                }

                else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }



}