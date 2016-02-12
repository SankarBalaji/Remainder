package com.remainder.sankar.sample;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashSet;
import java.util.Set;

import Listeners.DateClickListener;
import Listeners.NewRemainderCancelBtnListener;
import Listeners.NewRemainderOkBtnListener;
import Listeners.RemainderTypeListener;
import Listeners.TimeClickListener;
import Utils.AppConstants;


public class NewRemainder extends AppCompatActivity {
    private static final String[] TYPE = new String[] {
            "PHONE", "MESSAGE", "OTHER"
    };
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

        //Phone number fetcher
        final View contacts = findViewById (R.id.fetch_contacts);
        contacts.setEnabled(false);
        final View contactsButton = findViewById(R.id.fetch_contacts_button);
        contactsButton.setEnabled(false);

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled()) {
                    Intent myIntent = new Intent(NewRemainder.this, ListContactsActivity.class);
                    startActivityForResult(myIntent, AppConstants.REQUEST_NEWREM_CONTACTLIST);
                } else {
                    //Do nothing
                    Toast.makeText(getApplicationContext(),
                            "Please select a Remainder type", Toast.LENGTH_LONG).show();
                }


            }
        });

        //Spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TYPE);
        MaterialBetterSpinner remainderType = (MaterialBetterSpinner)
                findViewById(R.id.type);
        remainderType.setAdapter(adapter);

        Set<String> contactEnableStrings = new HashSet<>();
        contactEnableStrings.add("PHONE");
        contactEnableStrings.add("MESSAGE");

        Set<View> viewsToEnable = new HashSet<>();
        viewsToEnable.add(contacts);
        viewsToEnable.add(contactsButton);

        RemainderTypeListener itemClickListener = new RemainderTypeListener (adapter, contactEnableStrings, true, viewsToEnable);
        remainderType.setOnItemClickListener(itemClickListener);


        //Edit Text for time selection
        View startTime = findViewById (R.id.start_time);
        TimeClickListener timeListener = new TimeClickListener (getFragmentManager(), startTime);
        startTime.setOnClickListener(timeListener);

        //Switch
        SwitchCompat recurrenceSwitch = (SwitchCompat) findViewById(R.id.recurrenceButton);

        //OK Button pressed
        AppCompatButton okbtn = (AppCompatButton)findViewById(R.id.okbtn);
        NewRemainderOkBtnListener okBtnListener = new NewRemainderOkBtnListener(this);
        okbtn.setOnClickListener(okBtnListener);

        //Cancel Button pressed
        AppCompatButton cancelbtn = (AppCompatButton)findViewById(R.id.cancelbtn);
        NewRemainderCancelBtnListener cancelBtnListener = new NewRemainderCancelBtnListener(this);
        cancelbtn.setOnClickListener(cancelBtnListener);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_NEWREM_CONTACTLIST) {
            if(resultCode == AppConstants.RESULT_SUCCESS){
                String phonenumber=data.getStringExtra("phonenumber");
                EditText number = (EditText)findViewById(R.id.fetch_contacts);
                number.setText(phonenumber);

            }
        }
    }

}
