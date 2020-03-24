package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

import java.util.Stack;

public class GameProcedure {
    private static final int PAIR = 2;
    private static final int DURATION = 30000;
    private static final int TICK_DURATION = 1000;

    private final CountDownTimer timer;
    private GameTimerListener timerListener;
    private GameSymbolListener symbolListener;
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

    public void setTimerListener(GameTimerListener listener) {
        this.timerListener = listener;
    }

    public void setSymbolListener(GameSymbolListener symbolListener) {
        this.symbolListener = symbolListener;
    }

    public void putClickedSymbol(int position, String value) {
        Symbol s = new Symbol(position, value);
        clickedSymbols.push(s);

        if (clickedSymbols.size() == PAIR) {
            reportMatch();
            clickedSymbols.clear();
        }
    }

    private void reportMatch() {
        Symbol s1 = clickedSymbols.pop();
        Symbol s2 = clickedSymbols.pop();
        if (s1.value.equals(s2.value)) {
            pairsFound++;
            symbolListener.onSymbolMatch(s1.position, s2.position);
        } else {
            symbolListener.onSymbolMatchFail(s1.position, s2.position);
        }
    }

    public boolean gameWon() {
        return pairsFound == symbolCount / 2;
    }

    public void startTimer() {
        timer.start();
        timerListener.onGameTimeStart();
    }

    public void stopTimer() {
        timer.cancel();
    }

    public interface GameTimerListener {
        void onGameTimeStart();

        void onGameTimeTick(int elapsedSeconds);

        void onGameTimeStop();
    }

    public interface GameSymbolListener {
        void onSymbolMatch(int position1, int position2);

        void onSymbolMatchFail(int position1, int position2);
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
