package com.remainder.sankar.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import Utils.MyContacts;

public class IndividualContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_contact);

        if(getIntent().getExtras() != null) {
            MyContacts contact = (MyContacts) (getIntent().getExtras().get("contact"));
            System.out.println("**************************************");
            System.out.println("In Individual contact activity:"+contact.getName());
            System.out.println("**************************************");
        }

    }

}
