package Adapters;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.remainder.sankar.sample.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Utils.MyContacts;

/**
 * Created by root on 17/12/15.
 */
public class MyContactsAdapter extends ArrayAdapter<MyContacts> {

    private final Context context;
    private final List<MyContacts> myContacts = new ArrayList<>();

    private static String[] CONTACTS_PHOTO_PROJECTION = {
            ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    public MyContactsAdapter (Context context, List<MyContacts> contactsList){
        super(context, R.layout.activity_contact_list_actvity, contactsList);
        this.context = context;
        this.myContacts.addAll(contactsList);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contactView = inflater.inflate(R.layout.activity_contact_list_actvity, parent, false);
        TextView contactName = (TextView) contactView.findViewById(R.id.contact_names);
        ImageView contactPhoto = (ImageView) contactView.findViewById(R.id.contact_picture);
        contactName.setText(myContacts.get(position).getName());
        if (null == myContacts.get(position).getPhotoUri()) {
            contactPhoto.setImageResource(R.drawable.contacts);
        } else {
            Uri contactURI = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, myContacts.get(position).getId());
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getApplicationContext().getContentResolver(), contactURI);
            if (null != input) {
                contactPhoto.setImageBitmap(BitmapFactory.decodeStream(input));

            } else {

                byte[] photoBytes = null;

                Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, myContacts.get(position).getPhotoId());

                Cursor cursor = context.getContentResolver().query(photoUri, CONTACTS_PHOTO_PROJECTION, null, null, null);

                if (cursor.moveToFirst())
                    photoBytes = cursor.getBlob(0);
                if (null != photoBytes) {
                    contactPhoto.setImageBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
                } else {
                    contactPhoto.setImageResource(R.drawable.contacts);
                }

                /*Uri contactPhotoUri = Uri.withAppendedPath(contactURI, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                contactPhoto.setImageURI(contactPhotoUri);*/
            }

        }

        return contactView;
    }

    @Override
    public MyContacts getItem (int position){
        return myContacts.get(position);
    }

}
