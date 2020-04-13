package sk3m3l1io.duisburg.memogame.model.repos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.PlayerScore;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;

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
                scoresListener.onScoresLoad(getScoresFrom(snapshot));
            }

            private Map<Integer, Player> getScoresFrom(@NonNull DataSnapshot snapshot) {
                Map<Integer, Player> scores = new HashMap<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    PlayerScore r = s.getValue(PlayerScore.class);
                    scores.put(r.getScore(), r.getPlayer());
                }
                Log.d(LogUtils.TAG, "Returning scores are  "+scores.size());
                return scores;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveScore(int score, Player p) {
        PlayerScore dataToSave = new PlayerScore(score, p);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    boolean playerFound = p.getId().equals(ds.getKey());
                    if (playerFound) {
                        PlayerScore dataFound = ds.getValue(PlayerScore.class);
                        if (score > dataFound.getScore()) {
                            ref.child(p.getId()).setValue(dataToSave);
                        }
                        return;
                    }
                }

                ref.child(p.getId()).setValue(dataToSave);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
