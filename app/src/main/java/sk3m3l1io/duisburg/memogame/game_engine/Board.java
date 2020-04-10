package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private final List<Box> boxes;
    public Board(char s1,char s2,char s3,char s4,char s5,char s6,char cover) {
        boxes = new ArrayList<>(12);
        populateBoxes(s1, s2, s3, s4, s5, s6, cover);
    }

    private void populateBoxes(char s1, char s2, char s3, char s4, char s5, char s6, char cover) {
        Box b = new Box(s1, cover);
        boxes.add(b);
        boxes.add(b);
        b = new Box(s2, cover);
        boxes.add(b);
        boxes.add(b);
        b = new Box(s3, cover);
        boxes.add(b);
        boxes.add(b);
        b = new Box(s4, cover);
        boxes.add(b);
        boxes.add(b);
        b = new Box(s5, cover);
        boxes.add(b);
        boxes.add(b);
        b = new Box(s6, cover);
        boxes.add(b);
        boxes.add(b);
    }

    public char getSymbolAt(int position) {
        return boxes.get(position).symbol;
    }

    public char getCoverAt(int position) {
        return boxes.get(position).cover;
    }
}
