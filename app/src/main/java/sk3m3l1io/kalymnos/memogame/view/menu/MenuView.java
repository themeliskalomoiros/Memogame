package sk3m3l1io.kalymnos.memogame.view.menu;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface MenuView extends ViewMvc {
    interface MenuItemClickListener {
        void onLightningClick();

        void onRandomClick();

        void onArcadeClick();

        void onLeaderboardClick();

        void onUserClick();
    }

    void setMenuClickListener(MenuItemClickListener listener);

    void setUserIconToSignOut();

    void setUserIconToDefault();
}
