package sk3m3l1io.duisburg.memogame.controller;

import sk3m3l1io.duisburg.memogame.model.LightningScores;

public class ArcadeScorePage extends ScoresFragment {
    @Override
    public void initRepository() {
        repo = new LightningScores();
        repo.setScoresListener(this);
    }
}
