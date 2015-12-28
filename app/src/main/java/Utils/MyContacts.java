package Utils;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 17/12/15.
 */
public class MyContacts implements Parcelable {
    final long id;
    final String name;
    final String photoUri;
    final long photoId;
    final Map<Integer, Set<String>> phoneNumbers = new HashMap<>();
    Bitmap contactPicture;
    public static Creator<MyContacts> CREATOR = new Creator<MyContacts>() {
        @Override
        public MyContacts createFromParcel(Parcel source) {
            long c_id = source.readLong();
            String c_name = source.readString();
            String c_photoUri = source.readString();
            long c_photoId = source.readLong();
            Map<Integer, Set<String>> c_phonenumbers = new HashMap<>();
            source.readMap(c_phonenumbers, HashMap.class.getClassLoader());
            MyContacts c_mycontacts = new MyContacts (c_id, c_name, c_photoUri, c_photoId);
            for (Integer key : c_phonenumbers.keySet()){
                Set<String> numbers = c_phonenumbers.get(key);
                for (String number : numbers)
                    c_mycontacts.addNumber(key, number);
            }
            return c_mycontacts;

        }

        @Override
        public MyContacts[] newArray(int size) {
            return new MyContacts[0];
        }
    };

    public MyContacts (long id, String name, String photoUri, long photoId){
        this.id = id;
        this.name = name;
        this.photoUri = photoUri;
        this.photoId = photoId;

    }

    public void addNumber (int type, String number) {
        if (null == phoneNumbers.get(type))
            phoneNumbers.put(type, new HashSet<String>());
        phoneNumbers.get(type).add(number);
    }

    public Map<Integer, Set<String>> getAllPhonenumbers (){
        Map<Integer, Set<String>> toReturn = new HashMap<>();
        for (int key : phoneNumbers.keySet()) {
            toReturn.put(key, phoneNumbers.get(key));
        }
        return toReturn;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public long getPhotoId() {
        return photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyContacts that = (MyContacts) o;

        return name.equals(that.name);

    }

    public Bitmap getContactPicture() {
        return contactPicture;
    }

    public void setContactPicture(Bitmap contactPicture) {
        this.contactPicture = contactPicture;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getId());
        dest.writeString(this.getName());
        dest.writeString(this.getPhotoUri());
        dest.writeLong(this.getPhotoId());
        dest.writeMap(this.getAllPhonenumbers());
    }
}

