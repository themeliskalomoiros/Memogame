package sk3m3l1io.kalymnos.memogame.controller;

import sk3m3l1io.kalymnos.memogame.model.LightningScores;

public class ArcadeScorePage extends ScoresFragment {
    @Override
    public void initRepository() {
        repo = new LightningScores();
        repo.setScoresListener(this);
    }
}
