package sk3m3l1io.duisburg.memogame.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LightningScores extends FirebaseScoreRepository {
    private static final String LIGHTNING = "lightning";

    @Override
    protected DatabaseReference getDatabaseRef() {
        return FirebaseDatabase.getInstance().getReference().child(SCORES).child(LIGHTNING);
    }
}
