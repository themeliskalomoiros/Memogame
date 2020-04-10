package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class GameState {
    public static final int PAIR = 2;
    public static final int MAX_MATCHES = 6;

    private final char cover;
    private final Board board;
    private int matchCount;
    private final Stack<Integer> match;
    private PairMatchListener matchListener;
    private PairMatchCompletionListener matchCompletionListener;
    private SymbolAlreadyUncoveredListener symbolUncoveredListener;

    public GameState(char[] symbols, char cover)
            throws DuplicateSymbolsException, InvalidSymbolCountException, InvalidCoverException {
        throwOnInvalidSymbolCount(symbols);
        throwOnDuplicateSymbols(symbols);
        throwOnInvalidCover(symbols, cover);

        this.cover = cover;
        match = new Stack<>();
        board = new Board(symbols);
    }

    private void throwOnInvalidCover(char[] symbols, char cover) throws InvalidCoverException {
        Set<Character> set = new HashSet<>(7);
        set.add(symbols[0]);
        set.add(symbols[1]);
        set.add(symbols[2]);
        set.add(symbols[3]);
        set.add(symbols[4]);
        set.add(symbols[5]);
        set.add(cover);

        if (set.size() < 7)
            throw new InvalidCoverException();
    }

    private void throwOnInvalidSymbolCount(char[] symbols) throws InvalidSymbolCountException {
        if (symbols.length != MAX_MATCHES)
            throw new InvalidSymbolCountException();
    }

    private void throwOnDuplicateSymbols(char[] symbols) throws DuplicateSymbolsException {
        Set<Character> set = new HashSet<>(6);
        set.add(symbols[0]);
        set.add(symbols[1]);
        set.add(symbols[2]);
        set.add(symbols[3]);
        set.add(symbols[4]);
        set.add(symbols[5]);

        if (set.size() < 6)
            throw new DuplicateSymbolsException();
    }

    public char getSymbolAt(int position) {
        return board.getSymbolAt(position);
    }

    public char getCover() {
        return cover;
    }

    public void setPairMatchListener(PairMatchListener listener) {
        matchListener = listener;
    }

    public void uncover(int position) {
        handleUncoveringOfSymbolAt(position);

        if (match.size() == PAIR)
            reportPairMatch();
    }

    private void handleUncoveringOfSymbolAt(int position) {
        if (match.size() < PAIR) {
            boolean isSameSymbol = !match.isEmpty() && position == match.peek();
            if (isSameSymbol) {
                if (symbolUncoveredListener != null)
                    symbolUncoveredListener.onSymbolAlreadyUncovered(position);
            } else {
                match.push(position);
            }
        }
    }

    private void reportPairMatch() {
        int last = match.pop();
        int first = match.pop();

        boolean matchFound = getSymbolAt(first) == getSymbolAt(last);
        if (matchFound) {
            if (matchListener != null)
                matchListener.onPairMatch(first, last);

            if (matchCompletionListener != null && ++matchCount == MAX_MATCHES)
                matchCompletionListener.onPairMatchesCompleted();
        } else {
            if (matchListener != null)
                matchListener.onPairMatchFail(first, last);
        }

    }

    public void setPairMatchCompletionListener(PairMatchCompletionListener listener) {
        matchCompletionListener = listener;
    }

    public void setSymbolAlreadyUncoveredListener(SymbolAlreadyUncoveredListener listener) {
        symbolUncoveredListener = listener;
    }

    public interface PairMatchListener {
        void onPairMatch(int position1, int position2);

        void onPairMatchFail(int position1, int position2);
    }

    public interface PairMatchCompletionListener {
        void onPairMatchesCompleted();
    }

    public interface SymbolAlreadyUncoveredListener {
        void onSymbolAlreadyUncovered(int position);
    }
}
