package sk3m3l1io.kalymnos.memogame.model;

import java.util.Map;

public interface ScoreRepository {
    interface ScoreLoadListener {
        void onAllScoresLoaded(Map<String, Integer> scores);

        void onPlayerScoreLoaded(String playerEmail, int score);
    }

    void setScoreLoadListener(ScoreLoadListener listener);

    void loadScores();

    void loadScoreOf(String playerEmail);
}
