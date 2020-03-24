package sk3m3l1io.kalymnos.memogame.model;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Game;

public interface GameRepository {
    List<Game> getGames();
}
