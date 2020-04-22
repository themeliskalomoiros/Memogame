package sk3m3l1io.duisburg.memogame.view.game;

import sk3m3l1io.duisburg.memogame.view.GameContainerView;
import sk3m3l1io.duisburg.memogame.view.TimeView;

public interface TimeModeView extends TimeView, GameContainerView {
    void setCompletedGamesCount(int c);
}
