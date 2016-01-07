package com.remainder.sankar.sample;

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
        //context.startActivity(i);
    }
}
