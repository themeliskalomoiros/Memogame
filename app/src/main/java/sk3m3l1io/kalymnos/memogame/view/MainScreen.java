package sk3m3l1io.kalymnos.memogame.view;

public interface MainScreen extends ViewMvc {
    interface ClickListener {
        void onHotRoundClick();

        void onPractiseClick();

        void onSignInClick();
    }

    void setClickListener(ClickListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void setSignInButtonImage(int imageRes);

    void setPlayerName(String name);

    void showPlayerName();

    void hidePlayerName();
}
