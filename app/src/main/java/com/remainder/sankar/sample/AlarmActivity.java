package com.remainder.sankar.sample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;

import java.util.Calendar;

import Utils.AlarmNotification;
import Utils.AppUtils;

/**
 * Created by root on 7/1/16.
 */
public class AlarmActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("*******************************");
        System.out.println("I am alarmed");
        System.out.println("*******************************");

        String sDesc = intent.getStringExtra("sDesc");
        System.out.println("S desc:"+sDesc);
        String lDesc = intent.getStringExtra("lDesc");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        int actionCode = intent.getIntExtra("action", 0);


        // use System.currentTimeMillis() to have a unique ID for the pending intent
        Intent i = new Intent(context, NotificationHandlingActivity.class);
        i.putExtra("action", actionCode);
        i.putExtra("phoneNumber", phoneNumber);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(context).setContentTitle("MyRemainder").setContentText(sDesc)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
               // .setStyle(new Notification.BigTextStyle().bigText(lDesc))
                .setSmallIcon(R.drawable.alarm)
                .addAction(getAction (actionCode, pIntent, context, phoneNumber))
                .addAction(getAction(-1, pIntent, context, phoneNumber)).build();
        notificationManager.notify(0, n);
        //context.startActivity(i);
    }

    public Notification.Action getAction (int actionCode, PendingIntent pIntent, Context context, String phoneNumber){
        Notification.Action action = null;
        String title = "";
        int icon = -1;
        switch (actionCode){
            case 0:
                String phoneNumberToCAll = "tel:"+phoneNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneNumberToCAll));
                pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
                title = "Call";
                icon = R.drawable.call;
                break;
            case 1:
                title = "Message";
                System.out.println("Going to Send Message ??????????????????");
                icon =  R.drawable.message;
                Uri sms_uri = Uri.parse("smsto:"+phoneNumber);
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                sms_intent.putExtra("sms_body", "Good Morning ! how r U ?");
                pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), sms_intent, 0);
                break;
            default:
                title = "Dismiss";
                icon = R.drawable.dismiss;
                Intent dimissIntent = new Intent(context, NewRemainder.class);
                pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), dimissIntent, 0);
                break;

        }
        action = new Notification.Action.Builder(icon, title, pIntent).build();
        return action;
    }

    public static void setAlarm(AlarmNotification notification) {
        Context ctxt = notification.getContext();
        AlarmManager mgr=(AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
        String date = notification.getDate();
        String time = notification.getTime();

        Calendar c = AppUtils.getTime(date, time);
        mgr.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), getPendingIntentForAlarm(notification));

    }

    private static PendingIntent getPendingIntentForNotification (Context context, Intent i) {

        return(PendingIntent.getBroadcast(context, (int)System.currentTimeMillis() , i, 0));
    }

    private static PendingIntent getPendingIntentForAlarm(AlarmNotification notification) {
        Intent i=new Intent(notification.getContext(), AlarmActivity.class);
        i.putExtra("action", notification.getActionCode());
        i.putExtra("phoneNumber", notification.getPhoneNumber());
        i.putExtra("sDesc", notification.getsDesc());
        i.putExtra("lDesc", notification.getlDesc());
        return(PendingIntent.getBroadcast(notification.getContext(), notification.getNotificationId() , i, 0));
    }
}
