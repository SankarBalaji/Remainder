package com.remainder.sankar.sample;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

/**
 * Created by root on 9/12/15.
 */
public class DatabaseAPI {
    public DatabaseAPI (){

    }

    public boolean insertNewRemainder (AppCompatActivity activity, String desc, String type, String date, String time, int recurrency){
        DBHelper helper = new DBHelper (activity);
        return helper.insertRemainder (1, desc, type, date, time, 1);
    }

    public String getRemainder (Activity activity){
        DBHelper helper = new DBHelper (activity);
        Random r = new Random();
        Cursor currentCursor = helper.getRemainder(r.nextInt());
        int id = currentCursor.getInt(0);
        String desc = currentCursor.getString(1);
        String type = currentCursor.getString(2);
        String date = currentCursor.getString(3);
        String time = currentCursor.getString(4);
        int recurrency = currentCursor.getInt(5);
        String result = id + "--"+desc+"--"+type+"--"+date+"--"+time+"--"+recurrency;
        return result;
    }

    public String getAllRemainder (Activity activity){
        DBHelper helper = new DBHelper (activity);

        return helper.getAllRemainder();
    }


}
