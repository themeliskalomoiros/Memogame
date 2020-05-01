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
        return (int) (getGameAverageGamePoints() + getGamesCompletedPoints() + getAccuracyPoints() + getBadgePoints());
    }

    public double getAccuracyPoints() {
        return getGameAverageGamePoints() * getAccuracy();
    }

    public double getAccuracyPercent() {
        return getAccuracy() * 100;
    }

    private double getAccuracy() {
        if (getMatches() + getFailedMatches() != 0) {
            return (getMatches() * 1.0) / (getMatches() + getFailedMatches());
        }

        return 0;
    }

    public double getGamesCompletedPoints() {
        return getGameAverageGamePoints() * gamesCompleted * 0.001;
    }

    public double getGameAverageGamePoints() {
        return (timeHighScore + survivalHighScore) / 2.0;
    }

    public double getBadgePoints() {
        int sum = 0;
        if (survivalCompleted)
            sum += survivalHighScore * 0.01;

        if (timeCompleted)
            sum += timeHighScore * 0.01;

        return sum;
    }
}
