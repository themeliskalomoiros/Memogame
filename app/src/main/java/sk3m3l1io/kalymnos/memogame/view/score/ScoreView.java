package sk3m3l1io.kalymnos.memogame.view.score;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface ScoreView extends ViewMvc {
    void setScores(int[] scores, Player[] players);
}
