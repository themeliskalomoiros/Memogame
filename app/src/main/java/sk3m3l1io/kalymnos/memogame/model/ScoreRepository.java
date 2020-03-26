package sk3m3l1io.kalymnos.memogame.model;

import java.util.Map;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public interface ScoreRepository {
    Map<Player, Integer> getScores();

    int getScoreOf(Player p);
}
