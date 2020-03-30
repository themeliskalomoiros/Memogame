package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.dialogs.MessageDialog;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.CountDownTimerReporter;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.game.RandomViewImp;

import static sk3m3l1io.kalymnos.memogame.utils.RunnableUtils.runDelayed;

public class RandomModeActivity extends AppCompatActivity implements
        GameFragment.GameProgressListener,
        GameFragment.GameFragmentCreationListener,
        CountDownTimerReporter.TimeListener,
        MessageDialog.ResponseListener {
    private static final int TIME_INTERVAL = 100;
    private static final int GAME_DURATION = 20000;
    private static final String REPEAT_DIALOG = "repeat dialog";
    private static final String NEXT_GAME_DIALOG = "move to next dialog";

    private int currentGame;
    private RandomViewImp view;
    private List<Game> games;
    private CountDownTimerReporter timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(view.getRootView());
    }

    private void init() {
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        Collections.shuffle(games);
        view = new RandomViewImp(getLayoutInflater(), null);
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addGameFragment();
    }

    private void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getGameContainerId(), new GameFragment())
                .commitNow();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof GameFragment) {
            GameFragment f = (GameFragment) fragment;
            f.setGameFragmentCreationListener(this);
            Game g = games.get(currentGame);
            ArrayUtils.shuffle(g.getSymbols());
            f.setGame(g);
        }
    }

    @Override
    public void onGameFragmentViewCreated(GameFragment f) {
        f.disableUI();
        f.openAllSymbols();
        view.setTitle(getString(R.string.take_a_look));
        runDelayed(() -> {
            f.coverAllSymbols();
            f.enableUI();
            updateUI(games.get(currentGame));
            timer.begin();
        }, 3000);
    }

    private void updateUI(Game g) {
        view.setDifficulty(g.getDifficulty());
        view.setTimeProgress(GAME_DURATION);
        view.setTimeProgressMax(GAME_DURATION);
    }

    @Override
    public void onGameBegin() {
    }

    @Override
    public void onGameCompleted() {
        timer.cancel();
        view.setTitle(getString(R.string.victory));
        MessageDialog.showInstance(
                this,
                getSupportFragmentManager(),
                getString(R.string.next_game),
                NEXT_GAME_DIALOG);
    }

    @Override
    public void onTimerBegin() {
        view.setTitle(getString(R.string.play));
    }

    @Override
    public void onTimerTick(int elapsedMilli) {
        view.setTimeProgress(elapsedMilli);
    }

    @Override
    public void onTimerFinish() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getGameContainerId());
        if (f instanceof GameFragment) {
            ((GameFragment) f).freezeUI();
            view.setTitle(getString(R.string.defeat));
            view.setTimeProgress(0);
            MessageDialog.showInstance(
                    this,
                    getSupportFragmentManager(),
                    getString(R.string.repeat_game),
                    REPEAT_DIALOG);
        }
    }

    @Override
    public void onDialogPositiveResponse(MessageDialog dialog) {
        dialog.dismiss();
        if (dialog.getTag().equals(NEXT_GAME_DIALOG))
            setNextGame();

        timer.cancel();
        addGameFragment();
    }

    private void setNextGame() {
        if (currentGame < games.size() - 1) {
            ++currentGame;
        } else {
            Collections.shuffle(games);
            currentGame = 0;
        }
    }

    @Override
    public void onDialogNegativeResponse(MessageDialog dialog) {
        finish();
    }
}
