package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class InvalidSymbolException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "All symbols must have length equal to 1.";
    }
}
