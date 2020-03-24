package sk3m3l1io.kalymnos.memogame.view;

public interface MainScreen extends ViewMvc {
    interface PlayClickListener {
        void onPlayClick();
    }

    void setPlayClickListener(PlayClickListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
