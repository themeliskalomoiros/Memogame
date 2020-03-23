package sk3m3l1io.kalymnos.memogame.model;

import java.util.Map;

import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.pojos.Score;

public interface ScoreRepository {
    Map<Player,Score> getScores();

    Score getScoreOf(Player p);
}
