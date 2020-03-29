package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;

public interface RandomView extends TimerViewMvc {
    void setDifficulty(GameDifficulty difficulty);
}
