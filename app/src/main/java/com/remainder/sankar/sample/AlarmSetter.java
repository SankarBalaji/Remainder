package com.remainder.sankar.sample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by root on 7/1/16.
 */
public class AlarmSetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public static void setAlarm(Context ctxt) {
        AlarmManager mgr=(AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());


        mgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                30 * 1000,
                getPendingIntent(ctxt));
    }

    private static PendingIntent getPendingIntent(Context ctxt) {
        Intent i=new Intent(ctxt, OnAlarmReceiver.class);

        return(PendingIntent.getBroadcast(ctxt, 0, i, 0));
    }
}
