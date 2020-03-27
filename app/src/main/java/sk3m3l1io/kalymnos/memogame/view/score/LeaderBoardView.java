package sk3m3l1io.kalymnos.memogame.view.score;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface LeaderBoardView extends ViewMvc {
    int getFragmentContainerId();

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
