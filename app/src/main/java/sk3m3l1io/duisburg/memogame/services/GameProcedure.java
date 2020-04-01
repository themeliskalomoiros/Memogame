package sk3m3l1io.duisburg.memogame.services;

import androidx.annotation.Nullable;

import java.util.Stack;

public final class GameProcedure {
    private static final int MATCH_COUNT = 2;

    private MatchListener matchListener;
    private CompletionListener completionListener;

    private final int symbolCount;
    private int pairsFound;
    private final Stack<Symbol> pair;

    public GameProcedure(int symbolCount) {
        this.symbolCount = symbolCount;
        pair = new Stack<>();
    }

    public void setMatchListener(MatchListener matchListener) {
        this.matchListener = matchListener;
    }

    public void setCompletionListener(CompletionListener completionListener) {
        this.completionListener = completionListener;
    }

    public void detachListeners() {
        completionListener = null;
        matchListener = null;
    }

    public void addTappedSymbol(int position, String value) {
        Symbol s = new Symbol(position, value);

        if (!pair.contains(s)) {
            pair.push(s);
        }

        if (pair.size() == MATCH_COUNT) {
            reportMatch();
            pair.clear();
        }

        if (gameWon()) {
            completionListener.onGameCompleted();
        }
    }

    private void reportMatch() {
        Symbol s1 = pair.pop();
        Symbol s2 = pair.pop();

        if (s1.value.equals(s2.value)) {
            matchListener.onMatch(s1.position, s2.position);
            pairsFound++;
        } else {
            matchListener.onMatchFailure(s1.position, s2.position);
        }
    }

    private boolean gameWon() {
        return pairsFound == symbolCount / 2;
    }

    public interface MatchListener {
        void onMatch(int position1, int position2);

        void onMatchFailure(int position1, int position2);
    }

    public interface CompletionListener {
        void onGameCompleted();
    }

    private class Symbol {
        int position;
        String value;

        public Symbol(int position, String value) {
            this.position = position;
            this.value = value;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (o == this) return true;
            if (!(o instanceof Symbol)) return false;

            Symbol other = (Symbol) o;
            return position == other.position && value.equals(other.value);
        }

        @Override
        public int hashCode() {
            return position ^ value.hashCode() * 31;
        }
    }
}
