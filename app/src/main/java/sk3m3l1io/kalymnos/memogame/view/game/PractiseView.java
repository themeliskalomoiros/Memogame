package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;

public interface PractiseView extends GameContainerViewMvc {
    interface ChangeGameClickListener {
        void onPreviousGameClick();

        void onNextGameClick();
    }

    void setChangeGameClickListener(ChangeGameClickListener listener);

    void setDifficulty(GameDifficulty difficulty);
}
