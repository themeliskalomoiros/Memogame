package sk3m3l1io.kalymnos.memogame.services;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Game;

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
