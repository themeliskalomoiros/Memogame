package sk3m3l1io.duisburg.memogame.view.score;

import sk3m3l1io.duisburg.memogame.pojos.Player;
import sk3m3l1io.duisburg.memogame.view.TitleView;

public interface LeaderBoardView extends TitleView {
    interface GameModePageChangeListener {
        void onTimeModePage();

        void onSurvivalModePage();
    }

    void setScorePageChangeListener(GameModePageChangeListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void setUser(Player p);
}
