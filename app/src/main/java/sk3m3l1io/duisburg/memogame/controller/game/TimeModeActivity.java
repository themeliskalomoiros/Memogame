package sk3m3l1io.duisburg.memogame.controller.game;

import android.util.Log;

import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Game;
import sk3m3l1io.duisburg.memogame.services.CountDownTimerReporter;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
import sk3m3l1io.duisburg.memogame.view.game.TimeModeView;
import sk3m3l1io.duisburg.memogame.view.game.TimeModeViewImp;

public class TimeModeActivity extends ScoreActivity implements
        CountDownTimerReporter.TimeListener {
    private static final int TIME_INTERVAL = 100;
    private static final int GAME_DURATION = 20000;

    private CountDownTimerReporter timer;

    public TimeModeActivity() {
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
    }

    @Override
    protected void initView() {
        view = new TimeModeViewImp(getLayoutInflater(), null);
    }

    @Override
    protected GameFragment.ViewCreationListener getGameFragmentViewCreationListener() {
        return null;
    }

    @Override
    protected void onAttachGameFragment(GameFragment f) {
        if (currentGame < games.size()) {
            Log.d(LogUtils.TAG, "GameFragment UI updated");
            updateUiOnFragmentAttach();
        }
    }

    private void updateUiOnFragmentAttach() {
        TimeModeView v = (TimeModeView) view;
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
            updateUiBeforeShowingResult();
            addResultFragment();
        } else {
            currentGame++;
            RunnableUtils.runDelayed(() -> addGameFragment(), 200);
        }
    }

    private void updateUiBeforeShowingResult() {
        TimeModeView v = (TimeModeView) view;
        v.setCompletedGamesCount(getCompletedGamesCount());
    }

    @Override
    public void onGameMatch() {
        super.onGameMatch();
    }

    @Override
    public void onGameMatchFail() {
        super.onGameMatchFail();
    }

    @Override
    public void onTimerBegin() {
        TimeModeView v = (TimeModeView) view;
    }

    @Override
    public void onTimerTick(int elapsedMilli) {
        ((TimeModeView) view).setTimeProgress(elapsedMilli);
        if (isRunningOutOfTime()) {
            TimeModeView v = (TimeModeView) view;
            if (v.isTimerVisible()) {
                v.hideTimer();
            } else {
                v.showTimer();
            }
        }
    }

    private boolean isRunningOutOfTime() {
        TimeModeView v = (TimeModeView) view;
        double percent = (v.getTimerProgress() / (v.getTimerMaxProgress() * 1.0) * 100);
        return percent <= 35;
    }

    @Override
    public void onTimerFinish() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getGameContainerId());
        if (f instanceof GameFragment) {
            ((TimeModeView) view).setTimeProgress(0);
            view.setTitle(getString(R.string.time_up));
            addResultFragment();
        }
    }
}
