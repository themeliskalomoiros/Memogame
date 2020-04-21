package sk3m3l1io.duisburg.memogame.model.repos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RandomScores extends FirebaseScoreRepository {
    private static final String RANDOM = "random";

    @Override
    protected DatabaseReference getDatabaseRef() {
        return FirebaseDatabase.getInstance().getReference().child(SCORES).child(RANDOM);
    }

    @Override
    public void setHighScoreListener(HighScoreListener listener) {
        
    }

    @Override
    public void updateCompletedGames(int count) {

    }

    @Override
    public void updateMatches(int count) {

    }

    @Override
    public void updateFailedMatches(int count) {

    }
}
