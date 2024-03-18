package com.example.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    public static final String EVENT_SMS_FILTER = "SMS_FILTER";
    public static final String EVENT_SMS_MSG_KEY = "SMS_MSG_KEY";

    public static final String CATEGORY_SMS_FILTER = "CATEGORY_SMS_FILTER";
    public static final String CATEGORY_SMS_MSG_KEY = "SMS_MSG_KEY";


    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage message : messages) {
            String smsBody = message.getMessageBody();
            // Check the prefix of the SMS message
            if (smsBody != null && !smsBody.isEmpty()) {
                if (smsBody.startsWith("event:")) {
                    // Handle event-related message
                    Intent eventIntent = new Intent(EVENT_SMS_FILTER);
                    eventIntent.putExtra(EVENT_SMS_MSG_KEY, smsBody);
                    context.sendBroadcast(eventIntent);
                } else if (smsBody.startsWith("category:")) {
                    // Handle category-related message
                    Intent categoryIntent = new Intent(CATEGORY_SMS_FILTER);
                    categoryIntent.putExtra(CATEGORY_SMS_MSG_KEY, smsBody);
                    context.sendBroadcast(categoryIntent);
                } else {
                    Toast.makeText(context, "Invalid message format", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
