package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.view.GameContainerView;
import sk3m3l1io.kalymnos.memogame.view.TimeView;

public interface RandomView extends TimeView, GameContainerView {
    void setLives(int lives);
}
