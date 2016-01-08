package com.remainder.sankar.sample;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Calendar;

import Utils.AppConstants;

import Listeners.DateClickListener;
import Utils.AppUtils;


public class TodayScreen extends AppCompatActivity {

    public static volatile AppCompatActivity dbContext;
    private static volatile boolean dbCleanupDone = false;
    private static volatile DatabaseAPI db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbContext = this;
        //Clear and recreate tables , used only in testing
        db = DatabaseAPI.getDatabaseHandler(dbContext);
        System.out.println("Require DB Cleanup:????" + !dbCleanupDone);
        if (!dbCleanupDone) {
            db.clearDB();
            dbCleanupDone = true;
        }
        //Create
        setContentView(R.layout.activity_today_screen);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(tb);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbarlayout);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        FloatingActionButton newRemainder = (FloatingActionButton) findViewById(R.id.fab);
        newRemainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TodayScreen.this, NewRemainder.class);
                TodayScreen.this.startActivity(myIntent);
            }
        });


        //Today's screen date filter
        View dateFilter = actionBar.getCustomView().findViewById(R.id.searchfield);
        DateClickListener dateListener = new DateClickListener(getFragmentManager(), dateFilter);
        dateFilter.setOnClickListener(dateListener);

        ((EditText)dateFilter).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text changed to:" + s.toString());
                System.out.println("Date to search:" + s.toString());
                String result = db.getAllRemainder(s.toString());
                System.out.println("GOT result:*************" + result);
                TextView textView = (TextView) findViewById(R.id.textsample);
                textView.setText(result);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.menu_today_screen);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("I am in start");
        System.out.println("I am back after finishing the activity *********************");
        EditText searchDateField = (EditText)getActionBar().getCustomView().findViewById(R.id.searchfield);
        searchDateField.setText(AppConstants.TODAY);
        String searchDate = searchDateField.getText().toString();
        System.out.println("Date to search:" + searchDate);
        if ( searchDate!= null && AppConstants.TODAY.equalsIgnoreCase(searchDate)) {
            long now = System.currentTimeMillis();
            Calendar today = Calendar.getInstance();
            today.setTimeInMillis(now);
            int year = today.get(Calendar.YEAR);
            System.out.println("Date to search:YEAR" + year);
            int monthOfYear = today.get(Calendar.MONTH);
            System.out.println("Date to search:Month" + monthOfYear);
            int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
            System.out.println("Date to search:day" + dayOfMonth);
            searchDate = AppUtils.getDateString(year, monthOfYear, dayOfMonth);
        }
        System.out.println("Date to search:" + searchDate);
        String result = db.getAllRemainder(searchDate);
        System.out.println("GOT result:*************" + result);
        TextView textView = (TextView) findViewById(R.id.textsample);
        textView.setText(result);

        AlarmSetter.setAlarm(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("I am in resume");

    }

}
