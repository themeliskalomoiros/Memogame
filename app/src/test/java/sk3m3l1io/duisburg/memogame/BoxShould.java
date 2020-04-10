package sk3m3l1io.duisburg.memogame;

import org.junit.Before;
import org.junit.Test;

import sk3m3l1io.duisburg.memogame.game_engine.Box;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoxShould {

    private Box sut;

    @Before
    public void setUpSut() {
        sut = new Box('M', 'O');
    }

    @Test
    public void haveNonEmptySymbolByDefault() {
        assertNotEquals('\0', sut.symbol);
    }

    @Test
    public void haveNonEmptyCoverByDefault() {
        assertNotEquals('\0', sut.cover);
    }

    @Test
    public void beEqualWithAnotherBoxBySymbolAndCover(){
        Box other = new Box('M', 'O');

        assertEquals(sut, other);
    }
}
