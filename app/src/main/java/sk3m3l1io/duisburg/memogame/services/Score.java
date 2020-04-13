package sk3m3l1io.duisburg.memogame.services;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Game;

public final class Score {
    public static final int POINTS_EASY = 100;
    public static final int POINTS_NORMAL = 150;
    public static final int POINTS_HARD = 200;
    private static final int LIFE = 50;

    private Score() {
    }

    public static int calculate(int ellapsedTime, int lives) {
        return ellapsedTime / 10 + lives * LIFE;
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
