package sk3m3l1io.duisburg.memogame.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Arrays;

public final class Game implements Parcelable {
    private final String title;
    private final String cover;
    private final String[] symbols;
    private final GameDifficulty difficulty;

    public Game(
            String title,
            String cover,
            String[] symbols,
            GameDifficulty difficulty) {
        this.title = title;
        this.cover = cover;
        this.symbols = symbols;
        this.difficulty = difficulty;
    }

    protected Game(Parcel in) {
        title = in.readString();
        cover = in.readString();
        symbols = in.createStringArray();
        difficulty = GameDifficulty.values()[in.readInt()];
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

    public String getCover() {
        return cover;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) return true;
        if (!(o instanceof Game)) return false;

        Game other = (Game) o;
        return title.equals(other.title) &&
                cover == other.cover &&
                difficulty == other.difficulty &&
                Arrays.equals(symbols, other.symbols);
    }

    @Override
    public int hashCode() {
        return title.hashCode() ^ cover.hashCode() ^ symbols.hashCode() ^ difficulty.hashCode() * 1579;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(cover);
        dest.writeStringArray(symbols);
        dest.writeInt(difficulty.ordinal());
    }
}
