package sk3m3l1io.duisburg.memogame.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public final class Player implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String photoUrl;

    public Player() {
    }

    public Player(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    protected Player(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        photoUrl = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(photoUrl);
    }
}
