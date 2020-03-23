package sk3m3l1io.kalymnos.memogame.pojos;

import androidx.annotation.Nullable;

import java.util.Arrays;

public final class Game {
    private final String title;
    private final char cover;
    private final char[] symbols;

    public Game(String title, char cover, char[] symbols) {
        this.title = title;
        this.cover = cover;
        this.symbols = symbols;
    }

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
                Arrays.equals(symbols,other.symbols);
    }

    @Override
    public int hashCode() {
        return title.hashCode() ^ cover ^ symbols.hashCode() * 1579;
    }
}
