package sk3m3l1io.duisburg.memogame.controller;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.dialogs.MessageDialog;
import sk3m3l1io.duisburg.memogame.model.LightningScores;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.pojos.Player;
import sk3m3l1io.duisburg.memogame.services.CountDownTimerReporter;
import sk3m3l1io.duisburg.memogame.services.Score;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
import sk3m3l1io.duisburg.memogame.view.game.ArcadeView;
import sk3m3l1io.duisburg.memogame.view.game.ArcadeViewImp;

public class ArcadeActivity extends AppCompatActivity implements
        CountDownTimerReporter.TimeListener,
        GameEngineFragment.GameProcedureListener,
        MessageDialog.ResponseListener,
        ResultFragment.ResultButtonClickListener {
    private static final int TIME_INTERVAL = 100;
    private static final int GAME_DURATION = 20000;

    private ArcadeView view;
    private Player player;
    private CountDownTimerReporter timer;

    private int currentGame;
    private List<Game> games;
    private List<Game> gamesCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        shuffle(games);
        addGameFragment();
        setContentView(view.getRootView());
    }

    private void init() {
        gamesCompleted = new ArrayList<>();
        timer = new CountDownTimerReporter(GAME_DURATION, TIME_INTERVAL);
        timer.setTimeListener(this);
        player = getIntent().getParcelableExtra(Player.class.getSimpleName());
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        view = new ArcadeViewImp(getLayoutInflater(), null);
    }

    private void shuffle(List<Game> games) {
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

    private void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getGameContainerId(), createCurrentGameFragment())
                .commit();
    }

    private GameEngineFragment createCurrentGameFragment(){
        Game g = games.get(currentGame);
        ArrayUtils.shuffle(g.getSymbols());
        GameEngineFragment f = new GameEngineFragment();
        f.setGame(g);
        return f;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment f) {
        super.onAttachFragment(f);
        if (f instanceof GameEngineFragment) {
            if (currentGame < games.size() - 1){
                updateUI(games.get(currentGame));
            }
        }
    }

    private void updateUI(Game g) {
        view.setCompletedGamesCount(gamesCompleted.size());
        view.setDifficulty(g.getDifficulty());
        view.setTitle(g.getTitle());
        view.setTimeProgressMax(GAME_DURATION);
        view.setTimeProgress(GAME_DURATION);
    }

    @Override
    public void onGameStart() {
        Snackbar.make(view.getRootView(), R.string.game_started, Snackbar.LENGTH_SHORT).show();
        timer.begin();
    }

    @Override
    public void onTimerBegin() {
        view.setTitle(R.string.play);
    }

    @Override
    public void onTimerTick(int elapsedMilli) {
        view.setTimeProgress(elapsedMilli);
    }

    @Override
    public void onTimerFinish() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getGameContainerId());
        if (f instanceof GameFragment) {
            view.setTimeProgress(0);
            view.setTitle(getString(R.string.time_up));
            addResultFragment();
            saveScore();
        }
    }

    private void saveScore() {
        int score = Score.calculate(gamesCompleted);
        new LightningScores().saveScore(score, player);
    }

    private void addResultFragment() {
        ResultFragment f = new ResultFragment();
        f.setCompletedGames(gamesCompleted);
        f.setGameCount(games.size());
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getGameContainerId(), f)
                .commit();
    }

    @Override
    public void onGameComplete() {
        timer.cancel();
        gamesCompleted.add(games.get(currentGame));
        saveScore();

        if (gamesCompleted.size() == games.size()) {
            addResultFragment();
        } else {
            currentGame++;
            RunnableUtils.runDelayed(() -> addGameFragment(), 200);
        }
    }

    @Override
    public void onResultButtonClick() {
        recreate();
    }

    @Override
    public void onDialogPositiveResponse(MessageDialog dialog) {
        finish();
    }

    @Override
    public void onDialogNegativeResponse(MessageDialog dialog) {
    }

    @Override
    public void onBackPressed() {
        MessageDialog.show(this, getSupportFragmentManager(), getString(R.string.arcade_quit_message), null);
    }
}
