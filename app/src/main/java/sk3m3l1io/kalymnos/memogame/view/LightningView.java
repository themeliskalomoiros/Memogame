package sk3m3l1io.kalymnos.memogame.view;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;

public interface LightningView extends GameContainerViewMvc {
    void setGamesCompleted(int count);

    void setDifficulty(GameDifficulty difficulty);
}
