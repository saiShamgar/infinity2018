package com.example.sss.infinity.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.sss.infinity.OtpActivity;

public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle smsBundel=intent.getExtras();
        Object[] pds=(Object[])smsBundel.get("pdus");
        SmsMessage messages=SmsMessage.createFromPdu((byte[])pds[0]);

        String msg=messages.getOriginatingAddress().toString();
        if (msg.equalsIgnoreCase("HP-SHAMGR"))
        {
            Intent smsIntent=new Intent(context, OtpActivity.class);
            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            smsIntent.putExtra("messageNumber",messages.getOriginatingAddress());
            smsIntent.putExtra("message",messages.getMessageBody());
            context.startActivity(smsIntent);
            Toast.makeText(context,"Sms received from: "
                    +messages.getOriginatingAddress()+"\n"+messages.getMessageBody(),Toast.LENGTH_SHORT).show();



        }

        Intent local = new Intent();
        local.setAction("com.hello.action");
        context.startActivity(local);



    }




}
