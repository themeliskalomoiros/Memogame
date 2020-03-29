package sk3m3l1io.kalymnos.memogame.view.menu;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface MainView extends ViewMvc {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showPlayerName();

    void hidePlayerName();

    void setPlayerName(String name);

    int getMenuContainerId();
}
