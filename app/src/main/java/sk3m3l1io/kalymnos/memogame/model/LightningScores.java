package sk3m3l1io.kalymnos.memogame.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public class LightningScores extends FirebaseScoreRepository {
    private static final String LIGHTNING = "lightning";

    @Override
    protected DatabaseReference getDatabaseRef() {
        return FirebaseDatabase.getInstance().getReference().child(SCORES).child(LIGHTNING);
    }
}
