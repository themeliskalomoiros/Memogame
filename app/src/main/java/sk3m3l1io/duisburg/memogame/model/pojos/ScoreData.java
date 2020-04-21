package sk3m3l1io.duisburg.memogame.model.pojos;

public class ScoreData implements Comparable<ScoreData> {
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

    @Override
    public int compareTo(ScoreData o) {
        return o.getTotalPoints() - getTotalPoints();
    }

    public int getTotalPoints() {
        return (int) (getGamePoints() + getGamesCompletedPoints() + getAccuracyPoints() + getBadgePoints());
    }

    private double getAccuracyPoints() {
        if (failedMatches != 0) {
            return ((matches * 1.0) / failedMatches) * getGamePoints() * 0.1;
        }

        return 0;
    }

    private double getGamesCompletedPoints() {
        return getGamePoints() * gamesCompleted * 0.001;
    }

    private double getGamePoints() {
        return (timeHighScore + survivalHighScore) / 2.0;
    }

    private double getBadgePoints() {
        int sum = 0;
        if (survivalCompleted)
            sum += getGamePoints() * 0.01;

        if (timeCompleted)
            sum += getGamePoints() * 0.01;

        return sum;
    }
}
