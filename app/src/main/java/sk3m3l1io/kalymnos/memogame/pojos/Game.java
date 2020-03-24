package sk3m3l1io.kalymnos.memogame.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Arrays;

public final class Game implements Parcelable {
    private final String title;
    private final char cover;
    private final char[] symbols;

    public Game(String title, char cover, char[] symbols) {
        this.title = title;
        this.cover = cover;
        this.symbols = symbols;
    }

    protected Game(Parcel in) {
        title = in.readString();
        cover = (char) in.readInt();
        symbols = in.createCharArray();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public char getCover() {
        return cover;
    }

    public char[] getSymbols() {
        return symbols;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) return true;
        if (!(o instanceof Game)) return false;

        Game other = (Game) o;
        return title.equals(other.title) &&
                cover == other.cover &&
                Arrays.equals(symbols, other.symbols);
    }

    @Override
    public int hashCode() {
        return title.hashCode() ^ cover ^ symbols.hashCode() * 1579;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt((int) cover);
        dest.writeCharArray(symbols);
    }
}
