package sk3m3l1io.duisburg.memogame.view.score;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface ScoreView extends ViewMvc {
    interface OnScoreDetailsClickListener {
        void onScoreDetailsClicked(int position);
    }

    void setOnScoreDetailsClickListener(OnScoreDetailsClickListener listener);

    void setScores(List<ScoreData> scores, Player user);
}
