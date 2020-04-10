package sk3m3l1io.duisburg.memogame.game_engine;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class GameState {
    public static final int PAIR = 2;

    private final char cover;
    private final Board board;

    private int matchCount;
    private final Stack<Integer> matchHolder;

    private PairMatchListener matchListener;
    private PairMatchCompletionListener matchCompletionListener;
    private SymbolAlreadyUncoveredListener symbolUncoveredListener;

    public GameState(char[] symbols, char cover) throws InvalidCoverException {
        throwIfCoverIsInvalid(symbols, cover);
        this.cover = cover;
        matchHolder = new Stack<>();
        board = new Board(symbols);
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
        handleUncoveredSymbolAt(position);

        if (matchHolder.size() == PAIR)
            reportPairMatch();
    }

    public void setPairMatchCompletionListener(PairMatchCompletionListener listener) {
        matchCompletionListener = listener;
    }

    public void setSymbolAlreadyUncoveredListener(SymbolAlreadyUncoveredListener listener) {
        symbolUncoveredListener = listener;
    }

    private void throwIfCoverIsInvalid(char[] symbols, char cover) throws InvalidCoverException {
        Set<Character> set = new HashSet<>();
        for(char s : symbols){
            set.add(s);
        }
        set.add(cover);

        if (set.size() != (Board.SYMBOL_COUNT/2)+1)
            throw new InvalidCoverException();
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
                matchListener.onPairMatch(first, last);

            if (matchCompletionListener != null && ++matchCount == Board.SYMBOL_COUNT / 2)
                matchCompletionListener.onPairMatchesCompleted();
        } else {
            if (matchListener != null)
                matchListener.onPairMatchFail(first, last);
        }

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
