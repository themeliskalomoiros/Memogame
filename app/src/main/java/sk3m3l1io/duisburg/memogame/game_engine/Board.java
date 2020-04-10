package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Board {
    public static final int SYMBOL_COUNT = 12;

    private final char[] symbols;

    public Board(char[] symbols) {
        throwIfInvalidSymbolCount(symbols);
        throwIfSymbolsAreNotInPairs(symbols);
        this.symbols = symbols;
    }

    public char getSymbolAt(int position) {
        return symbols[position];
    }

    private void throwIfInvalidSymbolCount(char[] symbols) {
        if (symbols.length != SYMBOL_COUNT)
            throw new UnsupportedOperationException("Symbol count must be " + SYMBOL_COUNT);
    }

    private void throwIfSymbolsAreNotInPairs(char[] symbols) {
        List<Character> chars = new ArrayList<>(symbols.length);
        for(char s : symbols)
            chars.add(s);

        for(char c : chars){
            int timesFound = Collections.frequency(chars, c);
            if ( timesFound != 2)
                throw new UnsupportedOperationException("Symbol " + c + " found " + timesFound +
                        "times. Only 2 are allowed.");
        }
    }
}
