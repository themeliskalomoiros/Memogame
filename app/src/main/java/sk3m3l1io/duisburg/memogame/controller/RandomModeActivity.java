package sk3m3l1io.duisburg.memogame.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
import sk3m3l1io.duisburg.memogame.view.game.RandomViewImp;

public class RandomModeActivity extends AppCompatActivity implements
        GameEngineFragment.GameEventListener,
        GameEngineFragment.ViewCreationListener,
        ResultFragment.ResultButtonClickListener{
    private static final int DEFAULT_LIVES = 4;
    private static final int EXPOSURE_DURATION = 3000;

    private int lives = DEFAULT_LIVES;
    private RandomViewImp view;

    private int currentGame;
    private List<Game> games;
    private List<Game> gamesCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        shuffleByDifficulty(games);
        addGameFragment();
        setContentView(view.getRootView());
    }

    private void init() {
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        gamesCompleted = new ArrayList<>();
        view = new RandomViewImp(getLayoutInflater(), null);
    }

    private void shuffleByDifficulty(List<Game> games) {
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
        f.setViewCreationListener(this);
        f.setGame(g);
        return f;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment f) {
        super.onAttachFragment(f);
        if (f instanceof GameEngineFragment) {
            if (currentGame < games.size() - 1){
                updateUiOnFragmentAttach();
            }
        }
    }

    private void updateUiOnFragmentAttach() {
        Game g = games.get(currentGame);
        view.setDifficulty(g.getDifficulty());
        view.setTitle(g.getTitle());
        view.setLives(lives);
    }

    @Override
    public void onFragmentViewCreated(GameEngineFragment f) {
        view.setTitle(R.string.take_a_look);
        f.showAllSymbols();
        RunnableUtils.runDelayed(() -> {
            view.setTitle(games.get(currentGame).getTitle());
            f.coverAllSymbols();
        }, EXPOSURE_DURATION);
    }

    @Override
    protected void onStart() {
        super.onStart();
        exposeGameFor(EXPOSURE_DURATION);
    }

    private void exposeGameFor(int exposureDuration) {
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameComplete() {
        gamesCompleted.add(games.get(currentGame));
        saveScore();

        if (gamesCompleted.size() == games.size()) {
            addResultFragment();
        } else {
            currentGame++;
            lives = DEFAULT_LIVES;
            RunnableUtils.runDelayed(() -> addGameFragment(), 200);
        }
    }

    private void saveScore() {
        // TODO: Uncomment this
//        Player p = getIntent().getParcelableExtra(Player.class.getSimpleName());
//        int s = Score.calculate(ellapsedTime, lives);
//        new RandomScores().saveScore(s, p);
        Log.d(LogUtils.TAG, "score saved");
    }

    private void addResultFragment() {
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
    public void onGameMatchFail() {
        if(--lives == 0){
            addResultFragment();
        }
        view.setLives(lives);
    }

    @Override
    public void onResultButtonClick() {
        recreate();
    }
}
