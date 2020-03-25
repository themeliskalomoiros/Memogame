package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

public final class CountDownTimerReporter extends CountDownTimer{
    private TimeListener listener;

    public CountDownTimerReporter(int durationMilli, int tickMilli) {
        super(durationMilli, tickMilli);
    }

    public void setTimeListener(TimeListener timeListener) {
        listener = timeListener;
    }

    public void begin(){
        start();
        listener.onGameTimeBegin();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        listener.onGameTimeProgress((int) millisUntilFinished);
    }

    @Override
    public void onFinish() {
        listener.onGameTimeFinish();
    }

    public interface TimeListener {
        void onGameTimeBegin();

        void onGameTimeProgress(int elapsedMilli);

        void onGameTimeFinish();
    }
}
