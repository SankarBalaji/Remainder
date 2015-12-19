package com.remainder.sankar.sample;



import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.sql.Time;
import java.util.Calendar;

import Listeners.DateClickListener;
import Listeners.TimeClickListener;


public class NewRemainder extends AppCompatActivity {
    private static final String[] TYPE = new String[] {
            "PHONE", "MESSAGE"
    };

    static AppCompatActivity activity;
    LinearLayout contactsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remainder);
        //Recurrency switch
        SwitchCompat recurrenceButton = (SwitchCompat) findViewById(R.id.recurrenceButton);
        recurrenceButton.setGravity(Gravity.RIGHT);
        //Edit Text for date selection
        final View startDateView = findViewById (R.id.start_date);
        DateClickListener dateListener = new DateClickListener(getFragmentManager(), startDateView);
        startDateView.setOnClickListener(dateListener);

        //Contacts
        //contactsLayout = (LinearLayout)findViewById(R.id.contacts_test);
        //ContentResolver contactsResolver = getContentResolver();
        //Cursor contacts = contactsResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        //Phone number fetcher
        final View contacts = findViewById (R.id.fetch_contacts);

        //Spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TYPE);
        MaterialBetterSpinner remainderType = (MaterialBetterSpinner)
                findViewById(R.id.type);
        remainderType.setAdapter(adapter);
        remainderType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("********************");
                System.out.println("Spinner selected");
                System.out.println("Spinner position:"+position);
                System.out.println("Spinner selection:"+adapter.getItem(position));
                System.out.println("********************");

                if ("PHONE" == adapter.getItem(position)
                        || "MESSAGE" == adapter.getItem(position)) {
                    contacts.setEnabled(true);

                }

            }
        });


        //Edit Text for time Contcats

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NewRemainder.this, ContactListActvity.class);
                NewRemainder.this.startActivity(myIntent);
            }
        });


        //Edit Text for time selection
        View startTime = findViewById (R.id.start_time);
        TimeClickListener timeListener = new TimeClickListener (getFragmentManager(), startTime);
        startTime.setOnClickListener(timeListener);

        //Switch
        SwitchCompat recurrenceSwitch = (SwitchCompat) findViewById(R.id.recurrenceButton);

        //OK Button pressed
        AppCompatButton okbtn = (AppCompatButton)findViewById(R.id.okbtn);
        okbtn.setOnClickListener(new OkBtnListener ());

        //Cancel Button pressed
        AppCompatButton cancelbtn = (AppCompatButton)findViewById(R.id.cancelbtn);
        cancelbtn.setOnClickListener(new CancelBtnListener ());
        activity = this;


    }

    class OkBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            EditText descText = (EditText)findViewById(R.id.fName);
            String desc= descText.getText().toString();
            MaterialBetterSpinner spinner = (MaterialBetterSpinner)findViewById(R.id.type);
            String type = spinner.getText().toString();
            EditText dateText = (EditText)findViewById(R.id.start_date);
            String date= dateText.getText().toString();
            EditText timeText = (EditText)findViewById(R.id.start_time);
            String time= timeText.getText().toString();

            System.out.println("**************************");
            System.out.println("Ok clicked");
            System.out.println("Desc:"+desc);
            System.out.println("Type:"+type);
            System.out.println("Date:"+date);
            System.out.println("Time:"+time);
            System.out.println("**************************");
            DatabaseAPI db = new DatabaseAPI();
            db.insertNewRemainder(activity, desc, type, date, time, 1);
            finish();

        }
    }
    class CancelBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            System.out.println("**************************");
            System.out.println("cancel clicked");
            System.out.println("**************************");
            finish();

        }
    }

    class TimePickerListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            EditText time = (EditText)findViewById(R.id.start_time);
            String timeToSet = hourOfDay +":"+minute;
            time.setText(timeToSet);

        }
    }

}
