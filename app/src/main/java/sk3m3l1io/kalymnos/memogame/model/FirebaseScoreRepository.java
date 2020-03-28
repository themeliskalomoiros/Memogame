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

public class FirebaseScoreRepository implements ScoreRepository {
    private static final String REFERENCE = "scores";
    private static final String PLAYER = "player";
    private static final String SCORE = "score";

    private ScoreListener scoreListener;
    private DatabaseReference scoresRef;

    public FirebaseScoreRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        scoresRef = database.getReference(REFERENCE);
    }

    @Override
    public void setScoreListener(ScoreListener listener) {
        scoreListener = listener;
    }

    @Override
    public void loadScores() {
        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreListener.onScoresLoaded(getScoresFrom(snapshot));
            }

            private Map<Integer, Player> getScoresFrom(@NonNull DataSnapshot snapshot) {
                Map<Integer, Player> scores = new HashMap<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Record r = s.getValue(Record.class);
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
        Record dataToSave = new Record(score, player);
        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    boolean playerFound = player.getId().equals(ds.getKey());
                    if (playerFound) {
                        Record dataFound = ds.getValue(Record.class);
                        if (score > dataFound.score) {
                            scoresRef.child(player.getId()).setValue(dataToSave);
                        }
                        return;
                    }
                }

                scoresRef.child(player.getId()).setValue(dataToSave);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static class Record {
        int score;
        Player player;

        Record() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Record(int score, Player player) {
            this.score = score;
            this.player = player;
        }

        public int getScore() {
            return score;
        }

        public Player getPlayer() {
            return player;
        }
    }
}
