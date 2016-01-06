package com.remainder.sankar.sample;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Adapters.IndividualPhoneNumberAdapter;
import Utils.AppConstants;
import Utils.MyContacts;
import Utils.PhoneNumber;

public class IndividualContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static String[] CONTACTS_PHOTO_PROJECTION = {
            ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_contact);

        if(getIntent().getExtras() != null) {
            MyContacts contact = (MyContacts) (getIntent().getExtras().get("contact"));
            System.out.println("**************************************");
            System.out.println("In Individual contact activity:"+contact.getName());
            System.out.println("**************************************");
            Bitmap picture = (Bitmap) (getIntent().getExtras().getParcelable("picture"));
            ImageView contactPicture = (ImageView)findViewById(R.id.indvidual_contact_picture);
            TextView contactName = (TextView)findViewById(R.id.indvidual_contact_name);
            contactName.setText(contact.getName());
            if (null == picture)
                contactPicture.setImageResource(R.drawable.contacts);
            else
                contactPicture.setImageBitmap(picture);
            // Image

            /*InputStream photoStream = openDisplayPhoto(contact.getId());
            if (null == photoStream)
                contactPicture.setImageResource(R.drawable.contacts);
            else {
                Bitmap contact_picture = BitmapFactory.decodeStream(photoStream);
                contactPicture.setImageBitmap(contact_picture);
            }*/

            Map<Integer, Set<String>> numbers = contact.getAllPhonenumbers();
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            for (int key : numbers.keySet()){
                for (String num:numbers.get(key)){
                    phoneNumbers.add(new PhoneNumber(key, num));
                }
            }
            IndividualPhoneNumberAdapter phonenumberAdapter = new IndividualPhoneNumberAdapter (this, phoneNumbers);
            ListView phoneList = (ListView)findViewById(android.R.id.list);
            phoneList.setAdapter(phonenumberAdapter);
            phoneList.setOnItemClickListener(this);
        }

    }

    public InputStream openDisplayPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
        try {
            AssetFileDescriptor fd =
                    this.getContentResolver().openAssetFileDescriptor(displayPhotoUri, "r");
            return fd.createInputStream();
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PhoneNumber number = (PhoneNumber) parent.getAdapter().getItem(position);
        System.out.println("***********************************");
        System.out.println("selected Phonenumber:"+number.getNumber());
        System.out.println("selected Type:"+number.getType());
        System.out.println("***********************************");

        //Pass back the value
        Intent intent = new Intent();
        intent.putExtra("phonenumber", number.getNumber());
        setResult(AppConstants.RESULT_SUCCESS, intent);
        finish();
    }
}
