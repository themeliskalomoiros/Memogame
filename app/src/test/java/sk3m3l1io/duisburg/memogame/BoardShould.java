package sk3m3l1io.duisburg.memogame;

import org.junit.Before;
import org.junit.Test;

import sk3m3l1io.duisburg.memogame.game_engine.Board;
import sk3m3l1io.duisburg.memogame.game_engine.DuplicateSymbolsException;

import static org.junit.Assert.assertEquals;

public class BoardShould {
    private Board sut;

    @Before
    public void setup() {
        sut = new Board(new char[]{'1', '2', '3', '4', '5', '6'});
    }

    @Test
    public void have12BoxesWithExpectedValuesAndCover() {
        assertEquals('1', sut.getSymbolAt(0));
        assertEquals('1', sut.getSymbolAt(1));
        assertEquals('2', sut.getSymbolAt(2));
        assertEquals('2', sut.getSymbolAt(3));
        assertEquals('3', sut.getSymbolAt(4));
        assertEquals('3', sut.getSymbolAt(5));
        assertEquals('4', sut.getSymbolAt(6));
        assertEquals('4', sut.getSymbolAt(7));
        assertEquals('5', sut.getSymbolAt(8));
        assertEquals('5', sut.getSymbolAt(9));
        assertEquals('6', sut.getSymbolAt(10));
        assertEquals('6', sut.getSymbolAt(11));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwIndexOutOfBoundsWhenTryingToGetInvalidSymbol() {
        sut.getSymbolAt(12);
    }
}
