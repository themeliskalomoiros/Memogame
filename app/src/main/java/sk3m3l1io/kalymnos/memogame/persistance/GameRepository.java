package sk3m3l1io.kalymnos.memogame.persistance;

import sk3m3l1io.kalymnos.memogame.model.Game;

public interface GameRepository {
    Iterable<Game> getGames();
}
