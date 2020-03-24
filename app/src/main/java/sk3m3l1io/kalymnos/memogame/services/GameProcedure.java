package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

import java.util.Stack;

public class GameProcedure {
    private static final int PAIR = 2;
    private static final int DURATION = 30000;
    private static final int TICK_DURATION = 1000;

    private final CountDownTimer timer;
    private TimerListener timerListener;
    private MatchListener symbolListener;
    private ResultListener resultListener;

    private int pairsFound;
    private final int symbolCount;
    private final Stack<Symbol> clickedSymbols;

    public GameProcedure(int symbolCount) {
        pairsFound = 0;
        this.symbolCount = symbolCount;
        clickedSymbols = new Stack<>();
        timer = new CountDownTimer(DURATION, TICK_DURATION) {
            @Override
            public void onTick(long millisUntilFinished) {
                int elapsedSeconds = (int) millisUntilFinished / TICK_DURATION;
                timerListener.onGameTimeTick(elapsedSeconds);
            }

            @Override
            public void onFinish() {
                timerListener.onGameTimeStop();
            }
        };
    }

    public void setTimerListener(TimerListener listener) {
        this.timerListener = listener;
    }

    public void setSymbolListener(MatchListener symbolListener) {
        this.symbolListener = symbolListener;
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public void putClickedSymbol(int position, String value) {
        Symbol s = new Symbol(position, value);
        clickedSymbols.push(s);

        if (clickedSymbols.size() == PAIR) {
            handlePair();
            clickedSymbols.clear();
        }
    }

    private void handlePair() {
        Symbol s1 = clickedSymbols.pop();
        Symbol s2 = clickedSymbols.pop();

        boolean pairMatch = s1.value.equals(s2.value);
        if (pairMatch) {
            handlePairMatch(s1, s2);
        } else {
            symbolListener.onSymbolMatchFail(s1.position, s2.position);
        }
    }

    private void handlePairMatch(Symbol s1, Symbol s2) {
        symbolListener.onSymbolMatch(s1.position, s2.position);
        pairsFound++;
        boolean gameWon = pairsFound == symbolCount / 2;
        if (gameWon) {
            pairsFound = 0;
            resultListener.onGameWon();
        }
    }

    public void startTimer() {
        timer.start();
        timerListener.onGameTimeStart();
    }

    public void stopTimer() {
        timer.cancel();
    }

    public interface TimerListener {
        void onGameTimeStart();

        void onGameTimeTick(int elapsedSeconds);

        void onGameTimeStop();
    }

    public interface MatchListener {
        void onSymbolMatch(int position1, int position2);

        void onSymbolMatchFail(int position1, int position2);
    }

    public interface ResultListener {
        void onGameWon();

        void onGameLost();
    }

    private class Symbol {
        int position;
        String value;

        public Symbol(int position, String value) {
            this.position = position;
            this.value = value;
        }
    }
}
