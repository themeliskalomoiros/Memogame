package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.CountDownTimerReporter;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.LightningView;
import sk3m3l1io.kalymnos.memogame.view.LightningViewImp;

public class LightningRoundActivity extends AppCompatActivity implements
        CountDownTimerReporter.TimeListener,
        GameFragment.GameProgressListener {
    private static final int GAME_DURATION = 60000;
    private static final int TIME_INTERVAL = 100;

    private int currentGame;
    private List<Game> games;

    private LightningView view;
    private CountDownTimerReporter timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        view = new LightningViewImp(getLayoutInflater(), null);
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
        setContentView(view.getRootView());
        addGameFragment();
    }

    private void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(view.getGameContainerId(), new GameFragment())
                .commit();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof GameFragment) {
            GameFragment f = (GameFragment) fragment;
            Game g = games.get(currentGame);
            ArrayUtils.shuffle(g.getSymbols());
            f.setGame(g);
            updateUI(g);
        }
    }

    private void updateUI(Game g) {
        view.setTitle(g.getTitle());
        view.setTimeMaxProgress(GAME_DURATION);
        view.setTimeProgress(GAME_DURATION);
    }

    @Override
    public void onTimerBegin() {

    }

    @Override
    public void onTimerTick(int elapsedMilli) {

    }

    @Override
    public void onTimerFinish() {

    }

    @Override
    public void onGameBegin() {

    }

    @Override
    public void onGameCompleted() {

    }
}
