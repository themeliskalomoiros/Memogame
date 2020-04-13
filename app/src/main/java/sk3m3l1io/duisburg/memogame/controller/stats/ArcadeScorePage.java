package sk3m3l1io.duisburg.memogame.controller.stats;

import sk3m3l1io.duisburg.memogame.model.repos.LightningScores;

public class ArcadeScorePage extends ScoresFragment {
    @Override
    public void initRepository() {
        repo = new LightningScores();
        repo.setScoresListener(this);
    }
}
