package com.example.pgh_pharmacy_app;


import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MySmsReceiver extends BroadcastReceiver {
    private static final String TAG = MySmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";

    /**
     * Called when the BroadcastReceiver is receiving an Intent broadcast.
     *
     * @param context  The Context in which the receiver is running.
     * @param intent   The Intent received.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                strMessage += msgs[i].getMessageBody() + "\n";
                // Log and display the SMS message.
                Log.d(TAG, "onReceive: " + strMessage);
                if(strMessage.startsWith("BOTIKART: NOTIFICATION")) {
                    String[] temp =  strMessage.split("\n");
                    String[] temp1 = temp[1].split(" ");

                     NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New Notification")
                            .setContentText("Notifications")
                            .setAutoCancel(true);

                    if (temp1[0].equalsIgnoreCase("proceed")) {
                        Intent intent1 = new Intent(context, CounterCode.class);
                        PendingIntent intent2 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(intent2);
                    }
                    else if(temp1[0].equalsIgnoreCase("pended")){
                        Intent intent1 = new Intent(context, PendedCode.class);
                        PendingIntent intent2 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(intent2);
                    }
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
// notificationID allows you to update the notification later on.mNotificationManager.notify(notificationID, mBuilder.build());
                    Toast.makeText(context, temp1[0], Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
