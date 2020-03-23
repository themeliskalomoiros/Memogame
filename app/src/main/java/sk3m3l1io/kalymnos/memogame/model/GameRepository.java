package sk3m3l1io.kalymnos.memogame.model;

import sk3m3l1io.kalymnos.memogame.pojos.Game;

public interface GameRepository {
    Iterable<Game> getGames();
}
