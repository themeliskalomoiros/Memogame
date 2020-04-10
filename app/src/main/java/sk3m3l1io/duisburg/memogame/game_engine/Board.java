package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private static final int SYMBOL_COUNT = 12;

    private final char cover;
    private final List<Character> boxes;

    public Board(char s1,char s2,char s3,char s4,char s5,char s6,char cover) {
        this.cover = cover;
        boxes = new ArrayList<>(SYMBOL_COUNT);
        populateBoxes(s1, s2, s3, s4, s5, s6);
    }

    private void populateBoxes(char s1, char s2, char s3, char s4, char s5, char s6) {
        boxes.add(s1);
        boxes.add(s1);
        boxes.add(s2);
        boxes.add(s2);
        boxes.add(s3);
        boxes.add(s3);
        boxes.add(s4);
        boxes.add(s4);
        boxes.add(s5);
        boxes.add(s5);
        boxes.add(s6);
        boxes.add(s6);
    }

    public char getSymbolAt(int position) {
        return boxes.get(position);
    }

    public char getCoverAt(int position) {
        return boxes.get(position);
    }
}
