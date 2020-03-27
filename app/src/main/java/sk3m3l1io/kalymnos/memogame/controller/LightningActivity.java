package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.dialogs.MessageDialog;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.CountDownTimerReporter;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.game.LightningView;
import sk3m3l1io.kalymnos.memogame.view.game.LightningViewImp;

public class LightningActivity extends AppCompatActivity implements
        CountDownTimerReporter.TimeListener,
        GameFragment.GameProgressListener,
        MessageDialog.ResponseListener,
        ResultFragment.RestartClickListener {
    private static final int TIME_INTERVAL = 100;
    private static final int GAME_DURATION = 120000;

    private int currentGame = 0;
    private boolean firstGameBegun;
    private List<Game> games;
    private List<Game> gamesCompleted;

    private LightningView view;
    private CountDownTimerReporter timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        gamesCompleted = new ArrayList<>();
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        Collections.shuffle(games);
        view = new LightningViewImp(getLayoutInflater(), null);
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
        setContentView(view.getRootView());
        addGameFragment();
    }

    private void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getGameContainerId(), new GameFragment())
                .commit();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof GameFragment && currentGame < games.size() - 1) {
            GameFragment f = (GameFragment) fragment;
            Game g = games.get(++currentGame);
            ArrayUtils.shuffle(g.getSymbols());
            f.setGame(g);
            updateUI(g);
        }
    }

    private void updateUI(Game g) {
        view.setGamesCompleted(gamesCompleted.size());
        view.setDifficulty(g.getDifficulty());
        if (!firstGameBegun) {
            view.setTitle(getString(R.string.tap_to_start));
            view.setTimeMaxProgress(GAME_DURATION);
            view.setTimeProgress(GAME_DURATION);
        } else {
            view.setTitle(g.getTitle());
        }
    }

    @Override
    public void onTimerBegin() {
        if (!firstGameBegun)
            view.setTitle(getString(R.string.game_begun));
    }

    @Override
    public void onTimerTick(int elapsedMilli) {
        view.setTimeProgress(elapsedMilli);
    }

    @Override
    public void onTimerFinish() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getGameContainerId());
        if (f instanceof GameFragment) {
            view.setTitle(getString(R.string.time_up));
            view.setTimeProgress(0);
            addResultFragment();
        }
    }

    private void addResultFragment() {
        ResultFragment f = new ResultFragment();
        f.setGamesCompleted(gamesCompleted.size());
        f.setGameCount(games.size());
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getGameContainerId(), f)
                .commit();
    }

    @Override
    public void onGameBegin() {
        if (!firstGameBegun) {
            // Always call begin() instead of start to get a callback
            timer.begin();
            firstGameBegun = true;
        }
    }

    @Override
    public void onGameCompleted() {
        gamesCompleted.add(games.get(currentGame));
        if (gamesCompleted.size() == games.size()) {
            timer.cancel();
            addResultFragment();
        } else {
            addGameFragment();
        }
    }

    @Override
    public void onDialogPositiveResponse(MessageDialog dialog) {
        recreate();
    }

    @Override
    public void onDialogNegativeResponse(MessageDialog dialog) {
        finish();
    }

    @Override
    public void onRestartClick() {
        recreate();
    }
}
