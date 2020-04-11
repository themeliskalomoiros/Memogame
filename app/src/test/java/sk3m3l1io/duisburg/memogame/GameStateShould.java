package sk3m3l1io.duisburg.memogame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import sk3m3l1io.duisburg.memogame.game_engine.GameState;
import sk3m3l1io.duisburg.memogame.game_engine.InvalidCoverException;
import sk3m3l1io.duisburg.memogame.game_engine.InvalidSymbolException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameStateShould {
    private static final String SYMBOL_1 = "1";
    private static final String SYMBOL_2 = "2";
    private static final String SYMBOL_3 = "3";
    private static final String SYMBOL_4 = "4";
    private static final String SYMBOL_5 = "5";
    private static final String SYMBOL_6 = "6";
    private static final String COVER = "c";
    
    private GameState sut;
    private AtomicBoolean matchPredicate;
    private AtomicBoolean matchFailPredicate;
    private GameState.MatchListener matchListener;
    private AtomicBoolean matchCompletionPredicate;
    private GameState.GameCompletionListener gameCompletionListener;
    private AtomicBoolean symbolAlreadyUncoveredPredicate;
    private GameState.SymbolAlreadyUncoveredListener symbolAlreadyUncoveredListener;
    private AtomicBoolean startPredicate;
    private GameState.GameBeginListener gameBeginListener;

    @Before
    public void setup()
            throws InvalidCoverException, InvalidSymbolException {
        initPredicates();
        initListeners();
        initSut();
    }

    private void initPredicates() {
        matchPredicate = new AtomicBoolean(false);
        matchFailPredicate = new AtomicBoolean(false);
        matchCompletionPredicate = new AtomicBoolean(false);
        symbolAlreadyUncoveredPredicate = new AtomicBoolean(false);
        startPredicate = new AtomicBoolean(false);
    }

    private void initListeners() {
        matchListener = new GameState.MatchListener() {
            @Override
            public void onMatch(int position1, int position2) {
                matchPredicate.set(sut.getSymbolAt(position1) == sut.getSymbolAt(position2));
            }

            @Override
            public void onMatchFail(int position1, int position2) {
                matchFailPredicate.set(sut.getSymbolAt(position1) != sut.getSymbolAt(position2));
            }
        };
        gameCompletionListener = () -> matchCompletionPredicate.set(true);
        symbolAlreadyUncoveredListener = (pos) -> symbolAlreadyUncoveredPredicate.set(true);
        gameBeginListener = () -> startPredicate.set(true);
    }

    private void initSut()
            throws InvalidCoverException, InvalidSymbolException {
        String[] symbols = {
                SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6,
                SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6 };
        sut = new GameState(symbols, COVER);
        sut.setMatchListener(matchListener);
        sut.setGameCompletionListener(gameCompletionListener);
        sut.setSymbolAlreadyUncoveredListener(symbolAlreadyUncoveredListener);
        sut.setGameBeginListener(gameBeginListener);
    }

    @Test
    public void createValidBoard()  {
        assertEquals(SYMBOL_1, sut.getSymbolAt(0));
        assertEquals(SYMBOL_2, sut.getSymbolAt(1));
        assertEquals(SYMBOL_3, sut.getSymbolAt(2));
        assertEquals(SYMBOL_4, sut.getSymbolAt(3));
        assertEquals(SYMBOL_5, sut.getSymbolAt(4));
        assertEquals(SYMBOL_6, sut.getSymbolAt(5));
        assertEquals(SYMBOL_1, sut.getSymbolAt(6));
        assertEquals(SYMBOL_2, sut.getSymbolAt(7));
        assertEquals(SYMBOL_3, sut.getSymbolAt(8));
        assertEquals(SYMBOL_4, sut.getSymbolAt(9));
        assertEquals(SYMBOL_5, sut.getSymbolAt(10));
        assertEquals(SYMBOL_6, sut.getSymbolAt(11));
        assertEquals(COVER, sut.getCover());
    }

    @Test(expected = InvalidCoverException.class)
    public void throwInvalidCoverException()
            throws InvalidCoverException, InvalidSymbolException {
        String[] symbols = {SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6,
                SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6};
        sut = new GameState(symbols, SYMBOL_6);
    }

    @Test()
    public void notThrowInvalidCoverException()
            throws InvalidSymbolException {
        boolean threwException = false;
        String[] symbols = {SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6,
                SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6};

        try {
            sut = new GameState(symbols, COVER);
        } catch (InvalidCoverException e) {
            threwException = true;
        }

        assertFalse(threwException);
    }

    @Test(expected = InvalidSymbolException.class)
    public void throwInvalidSymbolException()
            throws InvalidCoverException, InvalidSymbolException {
        String[] symbols = {"Ela", SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6,
                "Ela", SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6};
        sut = new GameState(symbols, COVER);
    }

    @Test
    public void reportPairMatch() {
        sut.uncover(6);
        sut.uncover(0);

        assertTrue(matchPredicate.get());
    }

    @Test
    public void notReportPairMatch() {
        sut.uncover(2);
        sut.uncover(0);

        assertFalse(matchPredicate.get());
    }

    @Test
    public void reportPairMatchFailure() {
        sut.uncover(2);
        sut.uncover(1);

        assertTrue(matchFailPredicate.get());
    }

    @Test
    public void notReportPairMatchFailure() {
        sut.uncover(6);
        sut.uncover(0);

        assertFalse(matchFailPredicate.get());
    }

    @Test
    public void reportGameCompletion() {
        for (int i = 0; i < 6; i++) {
            sut.uncover(i);
            sut.uncover(i+6);
        }

        assertTrue(matchCompletionPredicate.get());
    }

    @Test
    public void notReportGameCompletion() {
        for (int i = 0; i < 12; i++) {
            if (i == 11) continue;
                sut.uncover(i);
        }

        assertFalse(matchCompletionPredicate.get());
    }

    @Test
    public void reportSymbolAlreadyUncovered() {
        sut.uncover(0);
        sut.uncover(0);
        sut.uncover(0);

        assertTrue(symbolAlreadyUncoveredPredicate.get());
    }

    @Test
    public void notReportSymbolAlreadyUncovered() {
        sut.uncover(0);
        sut.uncover(1);
        sut.uncover(2);

        assertFalse(symbolAlreadyUncoveredPredicate.get());
    }

    @Test
    public void reportStart() {
        sut.uncover(0);

        assertTrue(startPredicate.get());
    }

    @Test
    public void notReportStartTwice() {
        sut.uncover(0);
        assertTrue(startPredicate.get());
        startPredicate.set(false);

        sut.uncover(1);

        assertFalse(startPredicate.get());
    }

    @After
    public void tearDown() {
        sut.setMatchListener(null);
        sut.setGameCompletionListener(null);
        sut.setSymbolAlreadyUncoveredListener(null);
    }
}
