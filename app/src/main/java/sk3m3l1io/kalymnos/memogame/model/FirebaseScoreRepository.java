package sk3m3l1io.kalymnos.memogame.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public abstract class FirebaseScoreRepository implements ScoreRepository {
    protected static final String SCORES = "scores";

    protected DatabaseReference ref;
    private ScoresListener scoresListener;

    public FirebaseScoreRepository() {
        ref = getDatabaseRef();
    }

    protected abstract DatabaseReference getDatabaseRef();

    @Override
    public void setScoresListener(ScoresListener listener) {
        scoresListener = listener;
    }

    @Override
    public void loadScores() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoresListener.onScoresLoaded(getScoresFrom(snapshot));
            }

            private Map<Integer, Player> getScoresFrom(@NonNull DataSnapshot snapshot) {
                Map<Integer, Player> scores = new HashMap<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    FirebaseRecord r = s.getValue(FirebaseRecord.class);
                    scores.put(r.score, r.player);
                }
                return scores;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveScore(int score, Player player) {
        FirebaseRecord dataToSave = new FirebaseRecord(score, player);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    boolean playerFound = player.getId().equals(ds.getKey());
                    if (playerFound) {
                        FirebaseRecord dataFound = ds.getValue(FirebaseRecord.class);
                        if (score > dataFound.score) {
                            ref.child(player.getId()).setValue(dataToSave);
                        }
                        return;
                    }
                }

                ref.child(player.getId()).setValue(dataToSave);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
