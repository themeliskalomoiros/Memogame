package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class Box {
    public final char symbol, cover;

    public Box(char symbol, char cover) {
        this.symbol = symbol;
        this.cover = cover;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) return true;

        if (!(o instanceof Box)) return false;

        Box other = (Box)o;
        return symbol == other.symbol && cover == other.cover;
    }
}
