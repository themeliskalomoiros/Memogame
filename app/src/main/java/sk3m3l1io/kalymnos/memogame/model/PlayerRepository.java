package sk3m3l1io.kalymnos.memogame.model;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public interface PlayerRepository {
    interface PlayerDataListener {
        void onPlayersLoaded(List<Player> players);
    }

    void setPlayerDataListener(PlayerDataListener listener);

    void loadPlayers();
}
