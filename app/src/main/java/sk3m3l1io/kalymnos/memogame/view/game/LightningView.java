package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;

public interface LightningView extends TimerViewMvc {
    void setGamesCompleted(int count);

    void setDifficulty(GameDifficulty difficulty);
}
