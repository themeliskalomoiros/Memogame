package sk3m3l1io.kalymnos.memogame.services;

import android.os.CountDownTimer;

public final class CountDownTimerReporter extends CountDownTimer {
    private TimeListener listener;

    public CountDownTimerReporter(int durationMilli, int tickMilli) {
        super(durationMilli, tickMilli);
    }

    public void setTimeListener(TimeListener timeListener) {
        listener = timeListener;
    }

    public void begin() {
        start();
        listener.onTimerBegin();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        listener.onTimerTick((int) millisUntilFinished);
    }

    @Override
    public void onFinish() {
        listener.onTimerFinish();
    }

    public interface TimeListener {
        void onTimerBegin();

        void onTimerTick(int elapsedMilli);

        void onTimerFinish();
    }
}
