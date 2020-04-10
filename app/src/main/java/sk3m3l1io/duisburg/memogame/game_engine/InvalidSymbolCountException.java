package sk3m3l1io.duisburg.memogame.game_engine;

import androidx.annotation.Nullable;

public final class InvalidSymbolCountException extends Exception {
    @Nullable
    @Override
    public String getMessage() {
        return GameState.class.getSimpleName() + " supports only " + GameState.MAX_MATCHES + " symbols";
    }
}
