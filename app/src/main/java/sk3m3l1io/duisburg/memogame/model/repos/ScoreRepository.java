package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;

public interface ScoreRepository {
    interface ScoresListener {
        void onScoresLoad(List<ScoreData> scores);
    }

    interface PersonalRecordListener {
        void onPersonalRecordAchieved();
    }

    void setHighScoreListener(PersonalRecordListener listener);

    void setScoresListener(ScoresListener listener);

    void loadScores();

    void saveScore(int score, GameMode mode, Player p);

    void saveCompletedGames(int count, Player p);

    void saveMatches(int count, Player p);

    void saveFailedMatches(int count, Player p);

    void saveModeCompletion(GameMode mode, Player p);

    void savePlayer(Player p);
}
