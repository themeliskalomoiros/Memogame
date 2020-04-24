package sk3m3l1io.duisburg.memogame.model.repos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;

public class FirebaseScoreRepository implements ScoreRepository {
    private static final String VERSION_2 = "version_2";
    private static final String TIME_HIGHSCORE = "time_highscore";
    private static final String SURVIVAL_HIGHSCORE = "survival_highscore";
    private static final String GAMES_COMPLETED = "games_completed";
    private static final String MATCHES = "match_count";
    private static final String FAILED_MATCHES = "failed_match_count";
    private static final String TIME_COMPLETION = "time_completed";
    private static final String SURVIVAL_COMPLETION = "survival_completed";

    private final DatabaseReference rootRef;
    private ScoresListener scoresListener;
    private PersonalRecordListener highScoresListener;

    public FirebaseScoreRepository() {
        rootRef = FirebaseDatabase.getInstance().getReference().child(VERSION_2);
    }

    @Override
    public void setHighScoreListener(PersonalRecordListener listener) {
        highScoresListener = listener;
    }

    @Override
    public void setScoresListener(ScoresListener listener) {
        scoresListener = listener;
    }

    @Override
    public void loadScores() {
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ScoreData> scores = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    scores.add(getScoreData(s));
                }
                if (scoresListener != null)
                    scoresListener.onScoresLoad(scores);
            }

            private ScoreData getScoreData(DataSnapshot s) {
                Player p = s.child(Player.class.getSimpleName()).getValue(Player.class);

                int survivalHighScore = 0;
                int timeHighScore = 0;
                int gamesCompleted = 0;
                int matches = 0;
                int failedMatches = 0;
                boolean survivalCompleted = false;
                boolean timeCompleted = false;

                if (s.child(SURVIVAL_HIGHSCORE).getValue() != null)
                    survivalHighScore = s.child(SURVIVAL_HIGHSCORE).getValue(Integer.class);
                if (s.child(TIME_HIGHSCORE).getValue() != null)
                    timeHighScore = s.child(TIME_HIGHSCORE).getValue(Integer.class);
                if (s.child(GAMES_COMPLETED).getValue() != null)
                    gamesCompleted = s.child(GAMES_COMPLETED).getValue(Integer.class);
                if (s.child(MATCHES).getValue() != null)
                    matches = s.child(MATCHES).getValue(Integer.class);
                if (s.child(FAILED_MATCHES).getValue() != null)
                    failedMatches = s.child(FAILED_MATCHES).getValue(Integer.class);
                if (s.child(SURVIVAL_COMPLETION).getValue() != null)
                    survivalCompleted = s.child(SURVIVAL_COMPLETION).getValue(Boolean.class);
                if (s.child(TIME_COMPLETION).getValue() != null)
                    timeCompleted = s.child(TIME_COMPLETION).getValue(Boolean.class);

                return new ScoreData(matches, failedMatches, gamesCompleted, timeHighScore,
                        survivalHighScore, timeCompleted, survivalCompleted, p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveScore(int score, GameMode mode, Player p) {
        try {
            DatabaseReference ref = rootRef.child(p.getId()).child(getModeHighScoreKey(mode));
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (score > getCurrentScore(snapshot)) {
                        ref.setValue(score);

                        if (highScoresListener != null)
                            highScoresListener.onPersonalRecordAchieved();
                    }
                }

                private int getCurrentScore(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null)
                        return snapshot.getValue(Integer.class);

                    return 0;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getModeHighScoreKey(GameMode mode) throws Exception {
        switch (mode) {
            case TIME:
                return TIME_HIGHSCORE;
            case SURVIVAL:
                return SURVIVAL_HIGHSCORE;
            default:
                throw new Exception(mode + " must not have a key.");
        }
    }

    @Override
    public void updateCompletedGame(Player p) {
        DatabaseReference ref = rootRef.child(p.getId()).child(GAMES_COMPLETED);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref.setValue(getCurrentGames(dataSnapshot) + 1);
            }

            private int getCurrentGames(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                    return snapshot.getValue(Integer.class);

                return 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveMatches(int matches, Player p) {
        DatabaseReference ref = rootRef.child(p.getId()).child(MATCHES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref.setValue(matches + getCurrentMatches(snapshot));
            }

            private int getCurrentMatches(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                    return snapshot.getValue(Integer.class);

                return 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveFailedMatches(int matches, Player p) {
        DatabaseReference ref = rootRef.child(p.getId()).child(FAILED_MATCHES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref.setValue(matches + getCurrentMatches(snapshot));
            }

            private int getCurrentMatches(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                    return snapshot.getValue(Integer.class);

                return 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveModeCompletion(GameMode m, Player p) {
        try {
            rootRef.child(p.getId()).child(getModeCompletionKey(m)).setValue(true);
        } catch (Exception e) {
            Log.e(LogUtils.TAG, e.getMessage());
        }
    }

    private String getModeCompletionKey(GameMode m) throws Exception {
        switch (m) {
            case TIME:
                return TIME_COMPLETION;
            case SURVIVAL:
                return SURVIVAL_COMPLETION;
            default:
                throw new Exception(m + " must not have a key.");
        }
    }

    @Override
    public void savePlayer(Player p) {
        rootRef.child(p.getId()).child(Player.class.getSimpleName()).setValue(p);
    }
}
