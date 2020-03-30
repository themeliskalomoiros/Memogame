package sk3m3l1io.kalymnos.memogame.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RandomScores extends FirebaseScoreRepository {
    private static final String RANDOM = "random";

    @Override
    protected DatabaseReference getDatabaseRef() {
        return FirebaseDatabase.getInstance().getReference().child(SCORES).child(RANDOM);
    }
}
