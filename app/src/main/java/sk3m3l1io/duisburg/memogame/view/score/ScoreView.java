package sk3m3l1io.duisburg.memogame.view.score;

import sk3m3l1io.duisburg.memogame.pojos.Player;
import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface ScoreView extends ViewMvc {
    void setScores(int[] scores, Player[] players, Player user);
}
