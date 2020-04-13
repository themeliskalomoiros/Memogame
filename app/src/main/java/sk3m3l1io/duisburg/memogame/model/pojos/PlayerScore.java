package sk3m3l1io.duisburg.memogame.model.pojos;

public class PlayerScore {
    private int score;
    private Player player;

    PlayerScore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public PlayerScore(int score, Player player) {
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
