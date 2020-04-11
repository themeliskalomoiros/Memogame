package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Board {
    public static final int SYMBOL_COUNT = 12;

    private final String[] symbols;

    public Board(String[] symbols) {
        throwIfInvalidSymbolCount(symbols);
        throwIfSymbolsAreNotInPairs(symbols);
        this.symbols = symbols;
    }

    public String getSymbolAt(int position) {
        return symbols[position];
    }

    private void throwIfInvalidSymbolCount(String[] symbols) {
        if (symbols.length != SYMBOL_COUNT)
            throw new UnsupportedOperationException("Symbol count must be " + SYMBOL_COUNT);
    }

    private void throwIfSymbolsAreNotInPairs(String[] symbols) {
        List<String> list = new ArrayList<>(symbols.length);
        for(String s : symbols)
            list.add(s);

        for(String s : list){
            int timesFound = Collections.frequency(list, s);
            if ( timesFound != 2)
                throw new UnsupportedOperationException("Symbol " + s + " found " + timesFound +
                        "times. Only 2 are allowed.");
        }
    }
}
