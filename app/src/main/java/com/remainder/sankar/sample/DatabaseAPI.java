package com.remainder.sankar.sample;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

/**
 * Created by root on 9/12/15.
 */
public class DatabaseAPI {
    private final AppCompatActivity activity;
    private DatabaseAPI (AppCompatActivity activity){
        this.activity = activity;
    }
    private volatile static DatabaseAPI dbHandler;
    private volatile static DBHelper db;

    public static DatabaseAPI getDatabaseHandler (AppCompatActivity activity){
       if (null == dbHandler) {
           synchronized (new Object()) {
               if (null == dbHandler) {
                   dbHandler = new DatabaseAPI (activity);
                   db = new DBHelper (activity);
               }
           }
       }
        return dbHandler;
    }

    //USed only in testing
    public void clearDB (){
        db.clearTablesOnInstall();
    }

    public boolean insertNewRemainder (String desc, String type, String date, String time, int recurrency){
        int remId = db.getLastRemainderId();
        remId = remId + 1;
        db.insertRemainder (remId, desc, type, date, time, 1);
        db.insertRemId(remId);
        return true;
    }

    public String getRemainder (Activity activity){
        Random r = new Random();
        Cursor currentCursor = db.getRemainder(r.nextInt());
        int id = currentCursor.getInt(0);
        String desc = currentCursor.getString(1);
        String type = currentCursor.getString(2);
        String date = currentCursor.getString(3);
        String time = currentCursor.getString(4);
        int recurrency = currentCursor.getInt(5);
        String result = id + "--"+desc+"--"+type+"--"+date+"--"+time+"--"+recurrency;
        return result;
    }

    public String getAllRemainder (String date){
        return db.getAllRemainder(date);
    }


}
