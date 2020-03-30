package sk3m3l1io.kalymnos.memogame.model;

import java.util.Map;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoaded(Map<Integer, Player> scores);
    }

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void saveScore(int score, Player player);
}
