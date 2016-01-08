package com.remainder.sankar.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by root on 7/1/16.
 */
public class OnAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context, AlarmActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        System.out.println("*******************************");
        System.out.println("I am alarmed");
        System.out.println("*******************************");

        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(context).setContentTitle("MyRemainder").setContentText("I am alarmed")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.alarm).build();
        notificationManager.notify(0, n);
        //context.startActivity(i);
    }
}
