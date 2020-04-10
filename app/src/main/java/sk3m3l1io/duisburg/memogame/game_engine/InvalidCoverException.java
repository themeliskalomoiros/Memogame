package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class InvalidCoverException extends Exception {
    @Nullable
    @Override
    public String getMessage() {
        return "Invalid cover, a symbol with same value exists!";
    }
}
