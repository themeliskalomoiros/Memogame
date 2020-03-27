package sk3m3l1io.kalymnos.memogame.model;

import java.util.Map;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public interface ScoreRepository {
    interface ScoreListener {
        void onScoresLoaded(Map<Integer, Player> scores);
    }

    void setScoreListener(ScoreListener listener);

    void loadScores();
}
