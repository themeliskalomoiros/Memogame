package sk3m3l1io.kalymnos.memogame.view;

public interface MainView extends ViewMvc {
    interface ClickListener {
        void onLightningRoundClick();

        void onPractiseClick();

        void onSignInClick();

        void onLeaderBoardClick();
    }

    void setClickListener(ClickListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void setSignInButtonImage(int imageRes);

    void setPlayerName(String name);

    void showPlayerName();

    void hidePlayerName();
}
