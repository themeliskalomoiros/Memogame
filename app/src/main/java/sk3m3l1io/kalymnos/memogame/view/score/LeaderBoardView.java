package sk3m3l1io.kalymnos.memogame.view.score;

import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface LeaderBoardView extends ViewMvc {
    void setTitle(int titleRes);

    interface PageChangeListener {
        void onLightningPage();

        void onRandomPage();
    }

    void setScorePageChangeListener(PageChangeListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void set(Player user);
}
