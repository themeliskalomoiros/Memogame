package sk3m3l1io.kalymnos.memogame.persistance;

import java.util.Map;

import sk3m3l1io.kalymnos.memogame.model.Player;
import sk3m3l1io.kalymnos.memogame.model.Score;

public interface ScoreRepository {
    Map<Player,Score> getScores();

    Score getScoreOf(Player p);
}
