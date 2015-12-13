package com.example.shikhajain.shareride.smsListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by i ball on 12/12/2015.
 */
public  class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        Log.e("", msgBody);

                        if(msg_from.equalsIgnoreCase("RM-ALRNDR")) {
                            Log.e("", msgBody);

                           // OTPActivity otpActivity = new OTPActivity();
                            //otpActivity.codeUpdate(msgBody);
                            //codeUpdate(msgBody);

                            Bundle extras = intent.getExtras();
                            Intent intt = new Intent("com.example.shikhajain.shareride.smsListener.SmsListener");
                            // Data you need to pass to activity
                            intt.putExtra("code", msgBody);

                            context.sendBroadcast(intt);
                        }
                    }
                }catch(Exception e){
                         Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }


}