package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class DuplicateSymbolsException extends Exception {
    @Nullable
    @Override
    public String getMessage() {
        return "Found duplicate symbols or symbols with same value as the cover!";
    }
}
