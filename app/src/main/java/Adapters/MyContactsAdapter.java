package Adapters;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
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
public class MyContactsAdapter extends ArrayAdapter<MyContacts> implements Filterable {

    private final Context context;
    private List<MyContacts> filterable_Contacts = new ArrayList<>();
    private final List<MyContacts> orginalList = new ArrayList<>();

    private static String[] CONTACTS_PHOTO_PROJECTION = {
            ContactsContract.CommonDataKinds.Photo.PHOTO
    };

    public MyContactsAdapter (Context context, List<MyContacts> contactsList){
        super(context, R.layout.activity_contact_list_actvity, contactsList);
        this.context = context;
        this.orginalList.addAll(contactsList);
        this.filterable_Contacts.addAll(contactsList);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("My Contacts Adapter:position:"+position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contactView = inflater.inflate(R.layout.activity_contact_list_actvity, parent, false);
        TextView contactName = (TextView) contactView.findViewById(R.id.contact_names);
        ImageView contactPhoto = (ImageView) contactView.findViewById(R.id.contact_picture);
        if (position >= filterable_Contacts.size())
            return contactView;
        contactName.setText(filterable_Contacts.get(position).getName());
        if (null == filterable_Contacts.get(position).getPhotoUri()) {
            contactPhoto.setImageResource(R.drawable.contacts);
        } else {
            Uri contactURI = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, filterable_Contacts.get(position).getId());
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getApplicationContext().getContentResolver(), contactURI);
            if (null != input) {
                Bitmap picture = BitmapFactory.decodeStream(input);
                filterable_Contacts.get(position).setContactPicture(picture);
                contactPhoto.setImageBitmap(picture);

            } else {

                byte[] photoBytes = null;

                Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, filterable_Contacts.get(position).getPhotoId());

                Cursor cursor = context.getContentResolver().query(photoUri, CONTACTS_PHOTO_PROJECTION, null, null, null);

                if (cursor.moveToFirst())
                    photoBytes = cursor.getBlob(0);
                if (null != photoBytes) {
                    Bitmap picture = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                    filterable_Contacts.get(position).setContactPicture(picture);
                    contactPhoto.setImageBitmap(picture);
                } else {
                    contactPhoto.setImageResource(R.drawable.contacts);
                }

                /*Uri contactPhotoUri = Uri.withAppendedPath(contactURI, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                contactPhoto.setImageURI(contactPhotoUri);*/
            }

        }

        return contactView;
    }

    private class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<MyContacts> filteredContacts = new ArrayList<> ();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                //Do a Copy and operate on it
                filteredContacts.addAll(orginalList);
                results.values = filteredContacts;
                results.count = filteredContacts.size();
            }
            else {
                // We perform filtering operation

                String toCheck = constraint.toString().toUpperCase();
                System.out.println("Contraint to Check:"+toCheck);
                for (MyContacts contact : orginalList) {
                    String name = contact.getName().toUpperCase();
                    if (name.startsWith(toCheck)) {
                        System.out.println("Adding to filteredList:"+name);
                        filteredContacts.add(contact);
                    }

                }

            }
            results.values = filteredContacts;
            results.count = filteredContacts.size();
            System.out.println("filteredList size in filtering:"+results.count);
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            System.out.println("filteredList size in publishResults:"+results.count);
            for (MyContacts contact : (List<MyContacts>)results.values) {
                System.out.println("name  in publishResults:"+contact.getName());
            }
            filterable_Contacts = (List<MyContacts>) results.values;
            notifyDataSetChanged();
        }

    }

    private Filter myFilter;
    @Override
    public Filter getFilter() {
        if (myFilter == null)
            myFilter = new MyFilter();

        return myFilter;
    }
    @Override
    public int getCount() {
        return filterable_Contacts.size();
    }



    @Override
    public MyContacts getItem (int position){
        return filterable_Contacts.get(position);
    }

}
