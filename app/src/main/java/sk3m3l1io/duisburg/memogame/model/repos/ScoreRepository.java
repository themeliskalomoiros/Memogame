package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.PlayerScore;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoad(List<PlayerScore> scores);
    }

    interface HighScoreListener {
        void onHighScoreAchieved();
    }

    void setHighScoreListener(HighScoreListener listener);

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void savePlayerGameScore(Player p, GameMode mode, int score);

    void updateCompletedGames(int count);

    void updateMatches(int count);

    void updateFailedMatches(int count);
}
