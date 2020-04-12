package sk3m3l1io.duisburg.memogame.controller.game;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.LightningScores;
import sk3m3l1io.duisburg.memogame.model.ScoreRepository;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.services.CountDownTimerReporter;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
import sk3m3l1io.duisburg.memogame.view.game.ArcadeView;
import sk3m3l1io.duisburg.memogame.view.game.ArcadeViewImp;

public class ArcadeActivity extends ScoreActivity implements
        CountDownTimerReporter.TimeListener {
    private static final int TIME_INTERVAL = 100;
    private static final int GAME_DURATION = 20000;

    private CountDownTimerReporter timer;

    public ArcadeActivity() {
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
    }

    @Override
    protected void initView() {
        view = new ArcadeViewImp(getLayoutInflater(), null);
    }

    @Override
    protected GameFragment.ViewCreationListener getGameFragmentViewCreationListener() {
        return null;
    }

    @Override
    protected void onAttachGameFragment(GameFragment f) {
        if (currentGame < games.size() - 1){
            updateUiOnFragmentAttach();
        }
    }

    private void updateUiOnFragmentAttach() {
        ArcadeView v = (ArcadeView) view;
        Game g = games.get(currentGame);
        v.setCompletedGamesCount(getCompletedGamesCount());
        view.setDifficulty(g.getDifficulty());
        view.setTitle(g.getTitle());
        v.setTimeProgressMax(GAME_DURATION);
        v.setTimeProgress(GAME_DURATION);
    }

    @Override
    public void onGameStart() {
        timer.begin();
    }

    @Override
    public void onGameComplete() {
        super.onGameComplete();
        timer.cancel();

        if (getCompletedGamesCount() == games.size()) {
            addResultFragment();
        } else {
            currentGame++;
            RunnableUtils.runDelayed(() -> addGameFragment(), 200);
        }
    }

    @NonNull
    @Override
    protected ScoreRepository getScoreRepo() {
        return new LightningScores();
    }

    @Override
    public void onGameMatchFail() {

    }

    @Override
    public void onTimerBegin() {

    }

    @Override
    public void onTimerTick(int elapsedMilli) {
        ((ArcadeView)view).setTimeProgress(elapsedMilli);
    }

    @Override
    public void onTimerFinish() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getGameContainerId());
        if (f instanceof GameFragment) {
            ((ArcadeView)view).setTimeProgress(0);
            view.setTitle(getString(R.string.time_up));
            addResultFragment();
        }
    }
}
