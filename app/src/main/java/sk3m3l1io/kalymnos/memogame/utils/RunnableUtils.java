package sk3m3l1io.kalymnos.memogame.utils;

import android.os.Handler;
import android.os.Looper;

public final class RunnableUtils {
    private RunnableUtils() {
    }

    public static void runDelayed(Runnable runnable, int delayMillis) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            runnable.run();
        }, delayMillis);
    }
}
