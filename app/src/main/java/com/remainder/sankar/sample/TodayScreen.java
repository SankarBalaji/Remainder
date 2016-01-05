package com.remainder.sankar.sample;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import Listeners.DateClickListener;


public class TodayScreen extends AppCompatActivity {

    public static volatile AppCompatActivity dbContext;
    private static volatile boolean dbCleanupDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbContext = this;
        //Clear and recreate tables , used only in testing
        DatabaseAPI db = DatabaseAPI.getDatabaseHandler(dbContext);
        System.out.println ("Require DB Cleanup:????"+!dbCleanupDone);
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
        System.out.println("I am back after finishing the activity *********************");
        String result = db.getAllRemainder(this);
        System.out.println("GOT result:*************" + result);
        TextView textView = (TextView)findViewById(R.id.textsample);
        textView.setText(result);

        //Today's screen date filter
        View dateFilter = actionBar.getCustomView().findViewById(R.id.searchfield);
        DateClickListener dateListener = new DateClickListener(getFragmentManager(), dateFilter);
        dateFilter.setOnClickListener(dateListener);
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
}
