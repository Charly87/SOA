package com.example.cotizaciondolar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSBroadcast extends BroadcastReceiver {

    private static String SMS = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(SMS))
        {
            Bundle bundle = intent.getExtras();

            Object[] objects = (Object[]) bundle.get("pdus");

            SmsMessage[] messages = new SmsMessage[objects.length];
            for(int i=0; i < objects.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }

            Toast.makeText(context,messages[0].getMessageBody(),Toast.LENGTH_SHORT).show();
        }
    }
}
