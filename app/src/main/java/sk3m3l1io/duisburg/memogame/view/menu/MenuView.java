package sk3m3l1io.duisburg.memogame.view.menu;

import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface MenuView extends ViewMvc {
    interface SymbolClickListener {
        void onLightningClick();

        void onDiceClick();

        void onJoystickClick();

        void onLeaderboardClick();

        void onUserClick();
    }

    void setMenuClickListener(SymbolClickListener listener);

    void setUserIconToSignOut();

    void setUserIconToDefault();
}
