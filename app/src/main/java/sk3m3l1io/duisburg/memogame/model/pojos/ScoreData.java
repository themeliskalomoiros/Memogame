package sk3m3l1io.duisburg.memogame.model.pojos;

public class ScoreData {
    private int matches;
    private int failedMatches;
    private int gamesCompleted;
    private int timeHighScore;
    private int survivalHighScore;
    private boolean timeCompleted;
    private boolean survivalCompleted;
    private Player player;

    public ScoreData(int matches, int failedMatches, int gamesCompleted, int timeHighScore,
                     int survivalHighScore, boolean timeCompleted, boolean survivalCompleted, Player player) {
        this.matches = matches;
        this.failedMatches = failedMatches;
        this.gamesCompleted = gamesCompleted;
        this.timeHighScore = timeHighScore;
        this.survivalHighScore = survivalHighScore;
        this.timeCompleted = timeCompleted;
        this.survivalCompleted = survivalCompleted;
        this.player = player;
    }

    ScoreData() {
        // Required for Firebase
    }

    public int getMatches() {
        return matches;
    }

    public int getFailedMatches() {
        return failedMatches;
    }

    public int getGamesCompleted() {
        return gamesCompleted;
    }

    public int getTimeHighScore() {
        return timeHighScore;
    }

    public int getSurvivalHighScore() {
        return survivalHighScore;
    }

    public boolean isTimeCompleted() {
        return timeCompleted;
    }

    public boolean isSurvivalCompleted() {
        return survivalCompleted;
    }

    public Player getPlayer() {
        return player;
    }
}
