package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

import androidx.annotation.Nullable;

import java.util.Stack;

public class GameProcedure {
    private static final int PAIR = 2;
    private static final int TICK_DURATION = 100;

    private final CountDownTimer timer;
    private final int duration;
    private TimeListener timeListener;
    private PairMatchListener pairMatchListener;
    private ResultListener resultListener;

    private final int symbolCount;
    private int pairsFound;
    private final Stack<Symbol> pair;

    public GameProcedure(int symbolCount, int durationMilli) {
        this.symbolCount = symbolCount;
        duration = durationMilli;
        pair = new Stack<>();
        timer = new CountDownTimer(duration, TICK_DURATION) {
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

    public int getDuration() {
        return duration;
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

    public void resetState() {
        timer.cancel();
        pairsFound = 0;
        pair.clear();
    }

    public void detachListeners() {
        resultListener = null;
        pairMatchListener = null;
        timeListener = null;
    }

    public void addTappedSymbol(int position, String value) {
        Symbol s = new Symbol(position, value);

        if (!pair.contains(s)) {
            pair.push(s);
        }

        if (pair.size() == PAIR) {
            reportMatch();
            pair.clear();
        }

        if (gameWon()) {
            pairsFound = 0;
            resultListener.onGameWon();
        }
    }

    private void reportMatch() {
        Symbol s1 = pair.pop();
        Symbol s2 = pair.pop();

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
