package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class Box {
    public final char content, cover;

    public Box(char content, char cover) {
        this.content = content;
        this.cover = cover;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) return true;

        if (!(o instanceof Box)) return false;

        Box other = (Box)o;
        return content == other.content && cover == other.cover;
    }
}
