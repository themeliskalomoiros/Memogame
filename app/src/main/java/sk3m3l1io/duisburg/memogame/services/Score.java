package sk3m3l1io.duisburg.memogame.services;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Game;

public final class Score {
    public static final int POINTS_EASY = 100;
    public static final int POINTS_NORMAL = 150;
    public static final int POINTS_HARD = 200;

    private Score() {
    }

    public static int calculate(List<Game> completedGames) {
        int sum = 0;
        for (Game g : completedGames)
            sum += getPoints(g);

        return sum;
    }

    public static double gameModePoints(List<Game> completedGames, List<Double> accuracyPerGame) {
        double points = 0;
        for (int i = 0; i < completedGames.size(); i++) {
            Game g = completedGames.get(i);
            double a = accuracyPerGame.get(i);
            points += getPoints(g) * a;
        }
        return points;
    }

    public double totalScore(double survival, double time, int allGamesCompleted) {
        return survival * 0.495 + time * 0.495 + allGamesCompleted * 0.01;
    }

    private static int getPoints(Game g) {
        switch (g.getDifficulty()) {
            case EASY:
                return POINTS_EASY;
            case HARD:
                return POINTS_HARD;
            default:
                return POINTS_NORMAL;
        }
    }
}
