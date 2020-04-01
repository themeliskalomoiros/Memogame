package sk3m3l1io.duisburg.memogame.model;

import java.util.List;

import sk3m3l1io.duisburg.memogame.pojos.Game;

public interface GameRepository {
    List<Game> getGames();
}
