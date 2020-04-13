package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.Map;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoad(Map<Integer, Player> scores);
    }

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void saveScore(int score, Player p);
}
