package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private static final int SYMBOL_COUNT = 12;
    private final List<Character> boxes;

    public Board(char[] symbols) {
        boxes = new ArrayList<>(SYMBOL_COUNT);
        populateBoxesFrom(symbols);
    }

    private void populateBoxesFrom(char[] symbols) {
        boxes.add(symbols[0]);
        boxes.add(symbols[0]);
        boxes.add(symbols[1]);
        boxes.add(symbols[1]);
        boxes.add(symbols[2]);
        boxes.add(symbols[2]);
        boxes.add(symbols[3]);
        boxes.add(symbols[3]);
        boxes.add(symbols[4]);
        boxes.add(symbols[4]);
        boxes.add(symbols[5]);
        boxes.add(symbols[5]);
    }

    public char getSymbolAt(int position) {
        return boxes.get(position);
    }
}
