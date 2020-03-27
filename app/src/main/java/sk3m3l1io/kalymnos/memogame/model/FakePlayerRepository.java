package sk3m3l1io.kalymnos.memogame.model;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public class FakePlayerRepository implements PlayerRepository {
    private PlayerDataListener listener;

    @Override
    public void setPlayerDataListener(PlayerDataListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadPlayers() {
        new Thread(() -> {
            final Random r = new Random();
            final List<Player> list = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                int score = r.nextInt(40000);
                Player p = new Player("player" + i, "player" + i + "@mail.gr", null, score);
                list.add(p);
            }

            Collections.sort(list, (p1, p2) -> p2.getScore() - p1.getScore());
            delay(500);
            new Handler(Looper.getMainLooper()).post(() -> listener.onPlayersLoaded(list));
        }).start();
    }

    private void delay(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
        }
    }
}
