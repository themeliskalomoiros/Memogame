package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.PlayerScore;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoad(List<PlayerScore> scores);
    }

    // TODO: Implement a high score listener which publishes when a new high has being achieved.

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void savePlayerGameScore(Player p, GameMode mode, int score);
}
