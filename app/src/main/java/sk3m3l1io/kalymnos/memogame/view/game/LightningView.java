package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;
import sk3m3l1io.kalymnos.memogame.view.GameContainerView;
import sk3m3l1io.kalymnos.memogame.view.TimeView;

public interface LightningView extends TimeView, GameContainerView {
    void setGamesCompleted(int count);
}
