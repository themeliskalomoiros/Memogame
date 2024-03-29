package sk3m3l1io.duisburg.memogame;

import org.junit.Before;
import org.junit.Test;

import sk3m3l1io.duisburg.memogame.game_engine.Board;

import static org.junit.Assert.assertEquals;

public class BoardShould {
    private Board sut;

    @Before
    public void setup() {
        sut = new Board(new String[]{"1", "2", "3", "4", "5", "6","1", "2", "3", "4", "5", "6"});
    }

    @Test
    public void have12BoxesWithExpectedValuesAndCover() {
        assertEquals("1", sut.getSymbolAt(0));
        assertEquals("2", sut.getSymbolAt(1));
        assertEquals("3", sut.getSymbolAt(2));
        assertEquals("4", sut.getSymbolAt(3));
        assertEquals("5", sut.getSymbolAt(4));
        assertEquals("6", sut.getSymbolAt(5));
        assertEquals("1", sut.getSymbolAt(6));
        assertEquals("2", sut.getSymbolAt(7));
        assertEquals("3", sut.getSymbolAt(8));
        assertEquals("4", sut.getSymbolAt(9));
        assertEquals("5", sut.getSymbolAt(10));
        assertEquals("6", sut.getSymbolAt(11));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwIndexOutOfBoundsWhenTryingToGetInvalidSymbol() {
        sut.getSymbolAt(12);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void throwIfInvalidSymbolCount() {
        sut = new Board(new String[]{"1", "2", "3", "4", "5", "6"});
    }

    @Test(expected = UnsupportedOperationException.class)
    public void throwIfSymbolsAreNotInPairs() {
        sut = new Board(new String[]{"1", "2", "3", "4", "5", "6", "6", "2", "3", "4", "5", "6"});
    }
}
