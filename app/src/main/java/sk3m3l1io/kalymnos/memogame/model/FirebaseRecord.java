package sk3m3l1io.kalymnos.memogame.model;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

class FirebaseRecord {
    int score;
    Player player;

    FirebaseRecord() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public FirebaseRecord(int score, Player player) {
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
