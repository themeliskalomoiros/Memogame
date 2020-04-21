package sk3m3l1io.duisburg.memogame.model.repos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;

public class FirebaseScoreRepository implements ScoreRepository {
    private static final String VERSION_2 = "version 2";

    private final DatabaseReference ref;
    private ScoresListener scoresListener;
    private HighScoreListener highScoresListener;

    public FirebaseScoreRepository() {
        ref = FirebaseDatabase.getInstance().getReference().child(VERSION_2);
    }

    @Override
    public void setHighScoreListener(HighScoreListener listener) {
        highScoresListener = listener;
    }

    @Override
    public void setScoresListener(ScoresListener listener) {
        scoresListener = listener;
    }

    @Override
    public void loadScores() {

    }

    @Override
    public void saveScore(int score, GameMode mode, Player p) {

    }

    @Override
    public void updateCompletedGames(int count, Player p) {

    }

    @Override
    public void updateMatches(int count, Player p) {

    }

    @Override
    public void updateFailedMatches(int count, Player p) {

    }
}
