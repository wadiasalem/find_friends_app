package com.example.findfriends;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MySMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String messageBody,phoneNumber;
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle =intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    messageBody = messages[0].getMessageBody();
                    phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast.makeText(context,
                            "Message : "+messageBody+"Reçu de la part de;"+ phoneNumber,
                            Toast.LENGTH_LONG )
                            .show();
                    if(messageBody.contains("#FindFriends")){
                        /** lancer notification **/
                        NotificationCompat.Builder mynotif =
                                new NotificationCompat.Builder(
                                        context,
                                        "findFriends_channel");
                        mynotif.setContentTitle("Friend location");
                        mynotif.setContentText("You received your friend's location");
                        mynotif.setSmallIcon(android.R.drawable.ic_dialog_map);
                        mynotif.setAutoCancel(true);

                        mynotif.setVibrate(new long[]{ 500,1000,200,2000});

                        Uri son= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        mynotif.setSound(son);
                        NotificationManagerCompat manager=
                                NotificationManagerCompat.from(context);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            NotificationChannel canal=new
                                    NotificationChannel("findFriends_channel",
                                    "canal pour lapplication find me",
                                    NotificationManager.IMPORTANCE_DEFAULT);
                            AudioAttributes attr=new AudioAttributes.Builder()

                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_ALARM)
                                    .build();
                            canal.setSound(son,attr);
                            manager.createNotificationChannel(canal);
                        }

                        manager.notify(0,mynotif.build());
                        /**---------------------**/
                    }

                }
            }
        }

    }
}