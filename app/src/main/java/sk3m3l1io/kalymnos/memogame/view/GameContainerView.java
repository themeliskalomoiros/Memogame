package sk3m3l1io.kalymnos.memogame.view;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;

public interface GameContainerView extends TitleView {
    void setDifficulty(GameDifficulty difficulty);

    int getGameContainerId();
}
