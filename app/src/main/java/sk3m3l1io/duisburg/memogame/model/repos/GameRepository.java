package sk3m3l1io.duisburg.memogame.model.repos;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Game;

public interface GameRepository {
    List<Game> getGames();
}
