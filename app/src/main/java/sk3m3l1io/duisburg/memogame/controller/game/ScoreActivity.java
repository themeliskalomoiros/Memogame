package sk3m3l1io.duisburg.memogame.controller.game;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.model.repos.ScoreRepository;
import sk3m3l1io.duisburg.memogame.model.pojos.Game;
import sk3m3l1io.duisburg.memogame.model.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.services.Score;

public abstract class ScoreActivity extends GameActivity
        implements ResultFragment.ResultButtonClickListener {
    private final List<Game> gamesCompleted = new ArrayList<>();

    protected final int getCompletedGamesCount(){
        return gamesCompleted.size();
    }

    @Override
    protected final void arrangeGameSequence() {
        shuffleByDifficulty(games);
    }

    private void shuffleByDifficulty(List<Game> games) {
        // TODO: Refactor that
        Collections.sort(games, (g1, g2) -> g1.getDifficulty().compareTo(g2.getDifficulty()));
        int easyUpperBound = 0;
        int normalUpperBound = 0;

        for (int i = 0; i < games.size(); i++) {
            Game g1 = games.get(i);
            Game g2 = games.get(i + 1);

            if (g1.getDifficulty() == GameDifficulty.EASY &&
                    g2.getDifficulty() == GameDifficulty.NORMAL) {
                easyUpperBound = i;
                continue;
            }

            if (g1.getDifficulty() == GameDifficulty.NORMAL &&
                    g2.getDifficulty() == GameDifficulty.HARD) {
                normalUpperBound = i;
                break;
            }
        }

        Collections.shuffle(games.subList(0, easyUpperBound + 1));
        Collections.shuffle(games.subList(normalUpperBound, games.size()));
    }

    @Override
    public void onGameComplete() {
        gamesCompleted.add(games.get(currentGame));
        saveScore();
    }

    private void saveScore() {
        int s = Score.calculate(gamesCompleted);
        Player p = getIntent().getParcelableExtra(Player.class.getSimpleName());
        getScoreRepo().saveScore(s, p);
    }

    protected abstract @NonNull ScoreRepository getScoreRepo();

    protected final void addResultFragment() {
        ResultFragment f = new ResultFragment();
        f.setCompletedGames(gamesCompleted);
        f.setGameCount(games.size());
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(view.getGameContainerId(), f)
                .commit();
    }

    @Override
    public final void onResultButtonClick() {
        recreate();
    }
}
