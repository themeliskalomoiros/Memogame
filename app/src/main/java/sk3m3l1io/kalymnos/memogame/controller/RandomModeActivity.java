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

public class RandomModeActivity extends AppCompatActivity implements
        GameFragment.GameProgressListener,
        CountDownTimerReporter.TimeListener,
        MessageDialog.ResponseListener {
    private static final int GAME_DURATION = 20000;
    private static final int TIME_INTERVAL = 100;
    private static final String REPEAT_DIALOG = "repeat dialog";
    private static final String NEXT_GAME_DIALOG = "move to next dialog";

    private int currentGame;
    private RandomViewImp view;
    private List<Game> games;
    private CountDownTimerReporter timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        Collections.shuffle(games);
        view = new RandomViewImp(getLayoutInflater(), null);
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
        setContentView(view.getRootView());
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
            Game g = games.get(currentGame);
            ArrayUtils.shuffle(g.getSymbols());
            f.setGame(g);
            updateUI(g);
        }
    }

    private void updateUI(Game g) {
        view.setTitle(g.getTitle());
        view.setDifficulty(g.getDifficulty());
        view.setTimeProgress(GAME_DURATION);
        view.setTimeProgressMax(GAME_DURATION);
    }

    @Override
    public void onGameBegin() {
        // Always call begin() instead of start to get a callback
        timer.begin();
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
        if (dialog.getTag().equals(REPEAT_DIALOG)) {
            addGameFragment();
        } else if (dialog.getTag().equals(NEXT_GAME_DIALOG)) {
            moveToNextGame();
        }
    }

    private void moveToNextGame() {
        if (currentGame < games.size() - 1) {
            ++currentGame;
        }else{
            Collections.shuffle(games);
            currentGame = 0;
        }

        timer.cancel();
        addGameFragment();
    }

    @Override
    public void onDialogNegativeResponse(MessageDialog dialog) {
        finish();
    }
}
