package sk3m3l1io.duisburg.memogame.view.menu;

import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface MainView extends ViewMvc {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    int getMenuContainerId();
}
