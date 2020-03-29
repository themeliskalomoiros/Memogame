package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;
import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface PractiseView extends ViewMvc {
    interface ChangeGameClickListener {
        void onPreviousGameClick();

        void onNextGameClick();
    }

    void setTitle(String title);

    void setChangeGameClickListener(ChangeGameClickListener listener);

    void setDifficulty(GameDifficulty difficulty);

    int getGameContainerId();
}
