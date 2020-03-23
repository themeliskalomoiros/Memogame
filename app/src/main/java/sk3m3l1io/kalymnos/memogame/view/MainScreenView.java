package sk3m3l1io.kalymnos.memogame.view;

public interface MainScreenView extends ViewMvc {
    interface PlayClickListener {
        void onPlayClick();
    }

    void setPlayClickListener(PlayClickListener listener);
}
