package com.remainder.sankar.sample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.TYPE

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<MyContacts> contactsList = new ArrayList<>();
        Map<String, MyContacts> contacts = new HashMap<>();
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
            int type = cursor.getInt(cursor.getColumnIndex(CONTACTS_PROJECTION[5]));

            System.out.println("******************************");
            System.out.println("Contact ID :"+id);
            System.out.println("Contact name :"+name);
            System.out.println("Contact photo URI :"+photoUri);
            System.out.println("Contact number :"+phoneNumber);
            System.out.println("Contact Photo ID :"+photoId);
            System.out.println("Contact Phone Type :"+type);
            System.out.println("******************************");
            if (null == contacts.get(name)) {
                MyContacts contactToAdd = new MyContacts(id, name, photoUri, photoId);
                contactToAdd.addNumber(type, phoneNumber);
                contacts.put(name, contactToAdd);
            } else {
                contacts.get(name).addNumber(type, phoneNumber);
            }
        } while (cursor.moveToNext());

        contactsList.addAll(contacts.values());

        MyContactsAdapter contactsAdapter = new MyContactsAdapter(this, contactsList);
        setListAdapter(contactsAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        MyContacts contact = (MyContacts) getListAdapter().getItem(position);
        System.out.println("Selected Contact Name:" + contact.getName());
        Map<Integer, Set<String>> phonenumbers = contact.getAllPhonenumbers();
        for (int type : phonenumbers.keySet()) {
            Set<String> numbers = phonenumbers.get(type);
            for (String number : numbers) {
                System.out.println("Selected Contact Type:" + type+":Number:"+number);
            }
        }
        Intent myIntent = new Intent(ContactListActvity.this, IndividualContactActivity.class);;
        myIntent.putExtra("contact", contact);
        myIntent.putExtra("picture", contact.getContactPicture());
        ContactListActvity.this.startActivity(myIntent);

    }


}
