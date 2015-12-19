package Utils;

/**
 * Created by root on 17/12/15.
 */
public class MyContacts {
    final long id;
    final String name;
    final String photoUri;
    final long photoId;

    public MyContacts (long id, String name, String photoUri, long photoId){
        this.id = id;
        this.name = name;
        this.photoUri = photoUri;
        this.photoId = photoId;

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
}

