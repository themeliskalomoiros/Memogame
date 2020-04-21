package sk3m3l1io.duisburg.memogame.model.pojos;

public class ScoreData {
    private int matches;
    private int failedMatches;
    private int gamesCompleted;
    private int timeHighScore;
    private int survivalHighScore;
    private boolean timeCompleted;
    private boolean survivalCompleted;

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
}
