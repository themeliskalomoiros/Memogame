package sk3m3l1io.kalymnos.memogame.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FakeScoreRepository implements ScoreRepository {
    private ScoreLoadListener listener;

    @Override
    public void setScoreLoadListener(ScoreLoadListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadScores() {
        new Thread(()->{
            final Random r = new Random();
            final Map<String, Integer> scores = new HashMap<>();
            for (int i = 0; i < 30; i++) {
                String email = "player"+i+"@mail.gr";
                scores.put(email, r.nextInt(40000));
            }

            delay(500);
            listener.onAllScoresLoaded(scores);
        }).start();
    }

    @Override
    public void loadScoreOf(String playerEmail) {
        new Thread(()->{
            final int score = new Random().nextInt(40000);
            delay(200);
            listener.onPlayerScoreLoaded(playerEmail, score);
        }).start();
    }

    private void delay(int milli){
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {}
    }
}
