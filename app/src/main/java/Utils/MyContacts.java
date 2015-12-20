package Utils;

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
public class MyContacts {
    final long id;
    final String name;
    final String photoUri;
    final long photoId;
    final Map<Integer, Set<String>> phoneNumbers = new HashMap<>();

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

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

