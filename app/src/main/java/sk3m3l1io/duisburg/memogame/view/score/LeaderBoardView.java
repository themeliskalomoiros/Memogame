package sk3m3l1io.duisburg.memogame.view.score;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.view.TitleView;

public interface LeaderBoardView extends TitleView {
    interface PageChangeListener {
        void onTimeModePage();

        void onSurvivalModePage();
    }

    void setScorePageChangeListener(PageChangeListener listener);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void setUser(Player p);
}
