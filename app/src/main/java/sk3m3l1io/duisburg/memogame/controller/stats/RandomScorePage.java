package sk3m3l1io.duisburg.memogame.controller.stats;

import sk3m3l1io.duisburg.memogame.model.RandomScores;

public class RandomScorePage extends ScoresFragment {
    @Override
    public void initRepository() {
        repo = new RandomScores();
        repo.setScoresListener(this);
    }
}
