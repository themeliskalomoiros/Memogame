package sk3m3l1io.kalymnos.memogame.controller;

import sk3m3l1io.kalymnos.memogame.model.RandomScores;

public class RandomScorePage extends ScoresFragment {
    @Override
    public void initRepository() {
        repo = new RandomScores();
        repo.setScoresListener(this);
    }
}
