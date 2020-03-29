package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface TimerViewMvc extends ViewMvc {
    void setTitle(String title);

    void setTimeProgress(int progress);

    void setTimeMaxProgress(int progress);

    int getGameContainerId();
}
