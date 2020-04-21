package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoad(List<ScoreData> scores);
    }

    interface HighScoreListener {
        void onHighScoreAchieved();
    }

    void setHighScoreListener(HighScoreListener listener);

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void saveScore(int score, GameMode mode, Player p);

    void updateCompletedGames(int count, Player p);

    void updateMatches(int count, Player p);

    void updateFailedMatches(int count, Player p);
}
