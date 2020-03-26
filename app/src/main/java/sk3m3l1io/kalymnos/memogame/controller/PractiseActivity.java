package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.dialogs.MessageDialog;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.CountDownTimerReporter;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.PractiseView;
import sk3m3l1io.kalymnos.memogame.view.PractiseViewImp;

public class PractiseActivity extends AppCompatActivity implements
        GameFragment.GameProgressListener,
        MessageDialog.ResponseListener,
        PractiseView.ChangeGameClickListener,
        CountDownTimerReporter.TimeListener {
    private static final int GAME_DURATION = 20000;
    private static final int TIME_INTERVAL = 100;
    private static final String REPEAT_DIALOG = "repeat dialog";
    private static final String NEXT_GAME_DIALOG = "move to next dialog";

    private int currentGame;
    private List<Game> games;

    private PractiseView view;
    private CountDownTimerReporter timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        view = new PractiseViewImp(getLayoutInflater(), null);
        view.setChangeGameClickListener(this);
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
        setContentView(view.getRootView());
        addGameFragment();
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
    public void onPreviousGameClick() {
        if (currentGame > 0) {
            --currentGame;
            timer.cancel();
            addGameFragment();
        }
    }

    @Override
    public void onNextGameClick() {
        if (currentGame < games.size() - 1) {
            ++currentGame;
            timer.cancel();
            addGameFragment();
        }
    }

    private void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(view.getGameContainerId(), new GameFragment())
                .commit();
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
            view.setTitle(":(");
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
            onNextGameClick();
        }
    }

    @Override
    public void onDialogNegativeResponse(MessageDialog dialog) {
        finish();
    }
}
