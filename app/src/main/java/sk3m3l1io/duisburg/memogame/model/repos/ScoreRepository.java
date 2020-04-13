package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.List;
import java.util.Map;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.PlayerScore;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoad(List<PlayerScore> scores);
    }

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void saveScore(int score, Player p);
}
