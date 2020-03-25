package sk3m3l1io.kalymnos.memogame.view;

public interface MainScreen extends ViewMvc {
    interface ClickListener {
        void onPlayClick();

        void onSignInClick();
    }

    void setClickListener(ClickListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void setSignInButtonImage(int imageRes);
}
