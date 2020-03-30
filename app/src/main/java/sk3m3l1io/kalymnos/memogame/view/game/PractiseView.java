package sk3m3l1io.kalymnos.memogame.view.game;

import sk3m3l1io.kalymnos.memogame.view.GameContainerView;

public interface PractiseView extends GameContainerView {
    interface ChangeGameClickListener {
        void onPreviousGameClick();

        void onNextGameClick();
    }

    void setChangeGameClickListener(ChangeGameClickListener listener);
}
