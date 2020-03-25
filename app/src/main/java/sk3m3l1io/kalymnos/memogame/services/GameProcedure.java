package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

import androidx.annotation.Nullable;

import java.util.Stack;

public class GameProcedure {
    private static final int PAIR = 2;
    public static final int DURATION = 30000;
    private static final int TICK_DURATION = 1000;

    private final CountDownTimer timer;
    private TimeListener timeListener;
    private PairMatchListener pairMatchListener;
    private ResultListener resultListener;

    private int pairsFound = 0;
    private final int symbolCount;
    private final Stack<Symbol> clickedSymbols;

    public GameProcedure(int symbolCount) {
        this.symbolCount = symbolCount;
        clickedSymbols = new Stack<>();
        timer = new CountDownTimer(DURATION, TICK_DURATION) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeListener.onGameTimeProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timeListener.onGameTimeFinish();
            }
        };
    }

    public void setTimeListener(TimeListener listener) {
        this.timeListener = listener;
    }

    public void setPairMatchListener(PairMatchListener pairMatchListener) {
        this.pairMatchListener = pairMatchListener;
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public void detachListeners() {
        resultListener = null;
        pairMatchListener = null;
        timeListener = null;
    }

    public void putClickedSymbol(int position, String value) {
        Symbol s = new Symbol(position, value);

        if (!clickedSymbols.contains(s)) {
            clickedSymbols.push(s);
        }

        if (clickedSymbols.size() == PAIR) {
            reportMatch();
            clickedSymbols.clear();
        }

        if (gameWon()) {
            pairsFound = 0;
            resultListener.onGameWon();
        }
    }

    private void reportMatch() {
        Symbol s1 = clickedSymbols.pop();
        Symbol s2 = clickedSymbols.pop();

        boolean pairMatch = s1.value.equals(s2.value);
        if (pairMatch) {
            pairMatchListener.onPairMatch(s1.position, s2.position);
            pairsFound++;
        } else {
            pairMatchListener.onPairMatchFail(s1.position, s2.position);
        }
    }

    public boolean gameWon() {
        return pairsFound == symbolCount / 2;
    }

    public void begin() {
        timer.start();
        timeListener.onGameTimeBegin();
    }

    public void stop() {
        timer.cancel();
    }

    public interface TimeListener {
        void onGameTimeBegin();

        void onGameTimeProgress(int elapsedMilli);

        void onGameTimeFinish();
    }

    public interface PairMatchListener {
        void onPairMatch(int position1, int position2);

        void onPairMatchFail(int position1, int position2);
    }

    public interface ResultListener {
        void onGameWon();
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
