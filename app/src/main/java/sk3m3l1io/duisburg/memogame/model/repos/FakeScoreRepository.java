package sk3m3l1io.duisburg.memogame.model.repos;

import android.util.Log;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;

public class FakeScoreRepository implements ScoreRepository {

    @Override
    public void setHighScoreListener(PersonalRecordListener listener) {
        Log.d(LogUtils.TAG, "setHighScoreListener() called");
    }

    @Override
    public void setScoresListener(ScoresListener listener) {
        Log.d(LogUtils.TAG, "setScoresListener() called");
    }

    @Override
    public void loadScores() {
        Log.d(LogUtils.TAG, "loadScores() called");
    }

    @Override
    public void saveScore(int score, GameMode mode, Player p) {
        Log.d(LogUtils.TAG, "saveScore() called");
    }

    @Override
    public void updateCompletedGame(Player p) {
        Log.d(LogUtils.TAG, "updateCompletedGame() called");
    }

    @Override
    public void saveMatches(int count, Player p) {
        Log.d(LogUtils.TAG, "saveMatches() called");
    }

    @Override
    public void saveFailedMatches(int count, Player p) {
        Log.d(LogUtils.TAG, "saveFailedMatches() called");
    }

    @Override
    public void saveModeCompletion(GameMode mode, Player p) {
        Log.d(LogUtils.TAG, "saveModeCompletion() called");
    }

    @Override
    public void savePlayer(Player p) {
        Log.d(LogUtils.TAG, "savePlayer() called");
    }
}
