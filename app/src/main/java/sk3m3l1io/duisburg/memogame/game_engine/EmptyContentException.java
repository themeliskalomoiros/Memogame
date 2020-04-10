package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class EmptyContentException extends Exception {
    @Nullable
    @Override
    public String getMessage() {
        return "Box content must not be an empty character.";
    }
}
