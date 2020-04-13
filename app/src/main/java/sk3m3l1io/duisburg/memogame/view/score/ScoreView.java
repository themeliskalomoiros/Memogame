package sk3m3l1io.duisburg.memogame.view.score;

import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.PlayerScore;
import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface ScoreView extends ViewMvc {
    void setScores(List<PlayerScore> scores, Player user);
}
