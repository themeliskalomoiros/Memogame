package sk3m3l1io.kalymnos.memogame.view;

public interface MainScreenView extends ViewMvc {
    interface PlayActionListener {
        void onPlayActionInvoked();
    }

    void setPlayActionListener(PlayActionListener listener);
}
