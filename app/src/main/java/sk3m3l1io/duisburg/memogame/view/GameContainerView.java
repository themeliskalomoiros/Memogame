package sk3m3l1io.duisburg.memogame.view;

import sk3m3l1io.duisburg.memogame.model.pojos.GameDifficulty;

public interface GameContainerView extends TitleView {
    void setDifficulty(GameDifficulty difficulty);

    int getGameContainerId();
}
