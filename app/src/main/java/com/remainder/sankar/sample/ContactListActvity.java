package com.remainder.sankar.sample;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapters.MyContactsAdapter;
import Utils.MyContacts;


public class ContactListActvity extends ListActivity {
    private static Uri CONTACTS_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static String[] CONTACTS_PROJECTION = {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<MyContacts> contactsList = new ArrayList<>();
        Set<String> contacts = new HashSet<String>();
        //Load Contacts
        CursorLoader loader = new CursorLoader(this, CONTACTS_URI, CONTACTS_PROJECTION, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (!cursor.moveToFirst()){
            System.out.println("Problem loading Contacts");
            finish();
        }
        do {

            long id = cursor.getLong(cursor.getColumnIndex(CONTACTS_PROJECTION[0]));
            String name = cursor.getString(cursor.getColumnIndex(CONTACTS_PROJECTION[1]));
            String photoUri = cursor.getString(cursor.getColumnIndex(CONTACTS_PROJECTION[2]));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(CONTACTS_PROJECTION[3]));
            long photoId = cursor.getLong(cursor.getColumnIndex(CONTACTS_PROJECTION[4]));

            System.out.println("******************************");
            System.out.println("Contact ID :"+id);
            System.out.println("Contact name :"+name);
            System.out.println("Contact photo URI :"+photoUri);
            System.out.println("Contact number :"+phoneNumber);
            System.out.println("Contact Photo ID :"+photoId);
            System.out.println("******************************");
            if (contacts.add(name))
                contactsList.add(new MyContacts (id, name, photoUri, photoId));
        } while (cursor.moveToNext());

        MyContactsAdapter contactsAdapter = new MyContactsAdapter(this, contactsList);
        setListAdapter(contactsAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        MyContacts contact = (MyContacts) getListAdapter().getItem(position);
        System.out.println("Selected Contact:"+contact.getName());
    }


}
