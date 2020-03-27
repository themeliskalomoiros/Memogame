package sk3m3l1io.kalymnos.memogame.pojos;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public final class Player implements Parcelable {
    private final String name;
    private final String email;
    private final Uri photoUrl;
    private final int score;

    public Player(String name, String email, Uri photoUrl, int score) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) return true;
        if (!(o instanceof Player)) return false;
        Player other = (Player) o;
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode() * 1579;
    }

    protected Player(Parcel in) {
        name = in.readString();
        email = in.readString();
        photoUrl = in.readParcelable(Uri.class.getClassLoader());
        score = in.readInt();
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
        dest.writeString(name);
        dest.writeString(email);
        dest.writeParcelable(photoUrl, flags);
        dest.writeInt(score);
    }
}
