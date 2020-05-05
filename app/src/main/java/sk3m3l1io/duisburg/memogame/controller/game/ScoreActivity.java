package sk3m3l1io.duisburg.memogame.controller.game;

import android.view.Gravity;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Game;
import sk3m3l1io.duisburg.memogame.model.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.repos.FakeScoreRepository;
import sk3m3l1io.duisburg.memogame.model.repos.FirebaseScoreRepository;
import sk3m3l1io.duisburg.memogame.model.repos.ScoreRepository;
import sk3m3l1io.duisburg.memogame.services.Points;

public abstract class ScoreActivity extends GameActivity implements
        ResultFragment.ResultButtonClickListener,
        ScoreRepository.PersonalRecordListener {
    private final List<Game> gamesCompleted = new ArrayList<>();
    private final ScoreRepository repo;
    private int matches, failedMatches;

    public ScoreActivity() {
        repo = new FirebaseScoreRepository();
        repo.setHighScoreListener(this);
    }

    @Override
    protected final void arrangeGameSequence() {
        shuffleByDifficulty(games);
    }

    private void shuffleByDifficulty(List<Game> games) {
        Collections.sort(games, (g1, g2) -> g1.getDifficulty().compareTo(g2.getDifficulty()));
        int easyUpperBound = 0;
        int normalUpperBound = 0;

        for (int i = 0; i < games.size(); i++) {
            boolean nextGameExists = i + 1 < games.size();
            if (nextGameExists){
                Game g1 = games.get(i);
                Game g2 = games.get(i + 1);

                if (isLastEasyGame(g1, g2)) {
                    easyUpperBound = i;
                    continue;
                }

                if (isLastNormalGame(g1, g2)) {
                    normalUpperBound = i;
                    break;
                }
            }
        }

        Collections.shuffle(games.subList(0, easyUpperBound + 1));
        Collections.shuffle(games.subList(normalUpperBound, games.size()));
    }

    private boolean isLastEasyGame(Game g1, Game g2){
        return g1.getDifficulty() == GameDifficulty.EASY &&
                g2.getDifficulty() == GameDifficulty.NORMAL;
    }

    private boolean isLastNormalGame(Game g1, Game g2){
        return g1.getDifficulty() == GameDifficulty.NORMAL &&
                g2.getDifficulty() == GameDifficulty.HARD;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repo.setHighScoreListener(null);
    }

    protected final int getCompletedGamesCount() {
        return gamesCompleted.size();
    }

    @Override
    public void onGameComplete() {
        gamesCompleted.add(games.get(currentGame));
        saveScoreData();
    }

    private void saveScoreData() {
        int s = Points.calculate(gamesCompleted);
        Player p = getIntent().getParcelableExtra(Player.class.getSimpleName());
        repo.saveScore(s, gameMode, p);
        repo.updateCompletedGame(p);
        repo.saveMatches(matches, p);
        matches = 0;
        repo.saveFailedMatches(failedMatches, p);
        failedMatches = 0;
        if (gamesCompleted.size() == games.size())
            repo.saveModeCompletion(gameMode, p);
    }

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

    @Override
    public void onGameMatch() {
        matches++;
    }

    @Override
    public void onGameMatchFail() {
        failedMatches++;
    }

    @Override
    public final void onPersonalRecordAchieved() {
        Toast t = Toast.makeText(this, R.string.new_highscore, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }
}
