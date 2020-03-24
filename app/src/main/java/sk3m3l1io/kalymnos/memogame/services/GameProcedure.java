package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

public class GameProcedure {
    private static final int NO_SYMBOL = -1;
    private static final int DURATION = 30000;
    private static final int TICK_DURATION = 1000;

    private CountDownTimer timer;
    private GameTimeListener listener;

    private int currentSymbol = NO_SYMBOL;
    private int previousSymbol = NO_SYMBOL;
    private boolean[] symbolsClickState;

    public GameProcedure(int symbolCount) {
        symbolsClickState = new boolean[symbolCount];
        timer = new CountDownTimer(DURATION, TICK_DURATION) {
            @Override
            public void onTick(long millisUntilFinished) {
                int elapsedSeconds = (int) millisUntilFinished/TICK_DURATION;
                listener.onGameTimeTick(elapsedSeconds);
            }

            @Override
            public void onFinish() {
                listener.onGameTimeStop();
            }
        };
    }

    public void setGameTimeListener(GameTimeListener listener) {
        this.listener = listener;
    }

    public boolean isSymbolClicked(int position) {
        return symbolsClickState[position];
    }

    public void setSymbolClicked(int position) {
        symbolsClickState[position] = true;
    }

    public void setSymbolNotClicked(int position) {
        symbolsClickState[position] = false;
    }

    public boolean gameCompletedSuccessfully(){
        for(boolean state : symbolsClickState){
            if(state == false) return false;
        }

        return true;
    }

    public void startTimer(){
        timer.start();
        listener.onGameTimeStart();
    }

    public interface GameTimeListener{
        void onGameTimeStart();
        void onGameTimeTick(int elapsedSeconds);
        void onGameTimeStop();
    }
}
