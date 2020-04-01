package sk3m3l1io.duisburg.memogame.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.dialogs.MessageDialog;
import sk3m3l1io.duisburg.memogame.model.RandomScores;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.pojos.Player;
import sk3m3l1io.duisburg.memogame.services.CountDownTimerReporter;
import sk3m3l1io.duisburg.memogame.services.Score;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.view.game.RandomViewImp;

import static sk3m3l1io.duisburg.memogame.utils.RunnableUtils.runDelayed;

public class RandomModeActivity extends AppCompatActivity implements
        GameFragment.GameProgressListener,
        GameFragment.GameFragmentCreationListener,
        GameFragment.MatchFailListener,
        CountDownTimerReporter.TimeListener,
        MessageDialog.ResponseListener {
    private static final int TIME_INTERVAL = 100;
    private static final int GAME_DURATION = 20000;
    private static final String REPEAT_DIALOG = "repeat dialog";
    private static final String NEXT_GAME_DIALOG = "move to next dialog";

    private int lives;
    private int gameIndex;
    private int ellapsedTime;

    private List<Game> games;
    private RandomViewImp view;
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
            f.setGameFragmentCreationListener(this);
            f.setMatchFailListener(this);
            Game g = games.get(gameIndex);
            ArrayUtils.shuffle(g.getSymbols());
            f.setGame(g);
        }
    }

    @Override
    public void onGameFragmentViewCreated(GameFragment f) {
        f.disableUI();
        f.openAllSymbols();
        view.setTitle(getString(R.string.take_a_look));
        Game g = games.get(gameIndex);
        updateUI(g);
        view.setDifficulty(g.getDifficulty());
        runDelayed(() -> {
            f.coverAllSymbols();
            f.enableUI();
            timer.begin();
        }, 3000);
    }

    private void updateUI(Game g) {
        view.setDifficulty(g.getDifficulty());
        view.setTimeProgress(GAME_DURATION);
        view.setTimeProgressMax(GAME_DURATION);
        view.setDifficulty(g.getDifficulty());
        view.setLives(lives = getLivesOf(g.getDifficulty()));
    }

    private int getLivesOf(GameDifficulty d) {
        switch (d) {
            case EASY:
                return 3;
            case HARD:
                return 5;
            default:
                return 4;
        }
    }

    @Override
    public void onGameBegin() {
    }

    @Override
    public void onGameCompleted() {
        timer.cancel();
        view.setTitle(getString(R.string.victory));
        MessageDialog.show(this, getSupportFragmentManager(), getString(R.string.next_game), NEXT_GAME_DIALOG);
        saveScore();
    }

    private void saveScore() {
        Player p = getIntent().getParcelableExtra(Player.class.getSimpleName());
        int s = Score.calculate(ellapsedTime, lives);
        new RandomScores().saveScore(s, p);
        Log.d("malakia", "ellapsed time is " + ellapsedTime);
        Log.d("malakia", "lives are " + lives);
        Log.d("malakia", "score saved is " + s);
    }

    @Override
    public void onTimerBegin() {
        view.setTitle(getString(R.string.play));
    }

    @Override
    public void onTimerTick(int elapsedMilli) {
        view.setTimeProgress(ellapsedTime = elapsedMilli);
    }

    @Override
    public void onTimerFinish() {
        view.setTimeProgress(0);
        handleLoss(getString(R.string.time_up) + " " + getString(R.string.repeat_game));
    }

    private void handleLoss(String message) {
        timer.cancel();
        view.setTitle(getString(R.string.defeat));
        view.setLives(lives = 0);
        GameFragment f = (GameFragment) getSupportFragmentManager().findFragmentById(view.getGameContainerId());
        if (f != null) {
            f.freezeUI();
        }
        MessageDialog.show(this, getSupportFragmentManager(), message, REPEAT_DIALOG);
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
        if (gameIndex < games.size() - 1) {
            ++gameIndex;
        } else {
            Collections.shuffle(games);
            gameIndex = 0;
        }
    }

    @Override
    public void onDialogNegativeResponse(MessageDialog dialog) {
        finish();
    }

    @Override
    public void onMatchFail() {
        if (--lives > 0) {
            view.setLives(lives);
        } else {
            handleLoss(getString(R.string.no_more_live) + " " + getString(R.string.repeat_game));
        }
    }
}
