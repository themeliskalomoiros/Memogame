package sk3m3l1io.duisburg.memogame.game_engine;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class GameState {
    public static final int PAIR = 2;

    private final String cover;
    private final Board board;
    private int matchCount;
    private final Stack<Integer> matchHolder;

    private MatchListener matchListener;
    private GameCompletionListener gameCompletionListener;
    private SymbolAlreadyUncoveredListener symbolUncoveredListener;
    private boolean gameStarted;
    private GameBeginListener gameBeginListener;

    public GameState(String[] symbols, String cover)
            throws InvalidCoverException, InvalidSymbolException {
        throwIfCoverIsInvalid(symbols, cover);
        throwIfAnySymbolIsInvalid(symbols);
        this.cover = cover;
        matchHolder = new Stack<>();
        board = new Board(symbols);
    }

    private void throwIfCoverIsInvalid(String[] symbols, String cover)
            throws InvalidCoverException {
        Set<String> set = new HashSet<>();
        set.add(cover);
        for(String s : symbols)
            set.add(s);

        if (set.size() != (Board.SYMBOL_COUNT / 2) + 1)
            throw new InvalidCoverException();
    }

    private void throwIfAnySymbolIsInvalid(String[] symbols)
            throws InvalidSymbolException {
        for (String s : symbols)
            if(s.length() != 1)
                throw new InvalidSymbolException();
    }

    public String getSymbolAt(int position) {
        return board.getSymbolAt(position);
    }

    public String getCover() {
        return cover;
    }

    public void uncover(int position) {
        reportOnStart();

        handleUncoveredSymbolAt(position);

        if (matchHolder.size() == PAIR)
            reportPairMatch();
    }

    public void setMatchListener(MatchListener listener) {
        matchListener = listener;
    }

    public void setGameBeginListener(GameBeginListener listener){
        gameBeginListener = listener;
    }

    public void setGameCompletionListener(GameCompletionListener listener) {
        gameCompletionListener = listener;
    }

    public void setSymbolAlreadyUncoveredListener(SymbolAlreadyUncoveredListener listener) {
        symbolUncoveredListener = listener;
    }

    public void detachListeners(){
        symbolUncoveredListener = null;
        gameCompletionListener = null;
        matchListener = null;
        gameBeginListener = null;
    }

    private void reportOnStart() {
        if (!gameStarted){
            if (gameBeginListener != null)
                gameBeginListener.onGameBegin();
            gameStarted = true;
        }
    }

    private void handleUncoveredSymbolAt(int position) {
        if (matchHolder.size() < PAIR) {
            boolean isSameSymbol = !matchHolder.isEmpty() && position == matchHolder.peek();
            if (isSameSymbol) {
                if (symbolUncoveredListener != null)
                    symbolUncoveredListener.onSymbolAlreadyUncovered(position);
            } else {
                matchHolder.push(position);
            }
        }
    }

    private void reportPairMatch() {
        int last = matchHolder.pop();
        int first = matchHolder.pop();

        boolean matchFound = getSymbolAt(first) == getSymbolAt(last);
        if (matchFound) {
            if (matchListener != null)
                matchListener.onMatch(first, last);

            if (gameCompletionListener != null && ++matchCount == Board.SYMBOL_COUNT / 2)
                gameCompletionListener.onGameCompleted();
        } else {
            if (matchListener != null)
                matchListener.onMatchFail(first, last);
        }

    }

    public interface MatchListener {
        void onMatch(int position1, int position2);

        void onMatchFail(int position1, int position2);
    }

    public interface GameBeginListener {
        void onGameBegin();
    }

    public interface GameCompletionListener {
        void onGameCompleted();
    }

    public interface SymbolAlreadyUncoveredListener {
        void onSymbolAlreadyUncovered(int position);
    }
}
