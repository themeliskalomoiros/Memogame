package sk3m3l1io.duisburg.memogame.view.menu;

import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface MenuView extends ViewMvc {
    interface SymbolClickListener {
        void onTimeClick();

        void onSurvivalClick();

        void onPractiseClick();

        void onLeaderboardClick();

        void onSignInClick();
    }

    void setMenuClickListener(SymbolClickListener listener);

    void setSignIn();

    void setSignOut();
}
