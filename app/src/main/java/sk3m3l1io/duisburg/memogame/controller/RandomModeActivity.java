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
import sk3m3l1io.duisburg.memogame.model.RandomScores;
import sk3m3l1io.duisburg.memogame.model.ScoreRepository;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
import sk3m3l1io.duisburg.memogame.view.game.RandomView;
import sk3m3l1io.duisburg.memogame.view.game.RandomViewImp;

public class RandomModeActivity extends ScoreActivity implements
        GameFragment.GameEventListener,
        GameFragment.ViewCreationListener{
    private static final int DEFAULT_LIVES = 4;
    private static final int EXPOSURE_DURATION = 3000;

    private int lives = DEFAULT_LIVES;

    @Override
    protected void initView() {
        view = new RandomViewImp(getLayoutInflater(), null);
    }

    @Override
    protected GameFragment.ViewCreationListener getGameFragmentViewCreationListener() {
        return this;
    }

    @Override
    protected void onAttachGameFragment(GameFragment f) {
        if (currentGame < games.size() - 1){
            updateUiOnFragmentAttach();
        }
    }

    private void updateUiOnFragmentAttach() {
        Game g = games.get(currentGame);
        view.setDifficulty(g.getDifficulty());
        view.setTitle(g.getTitle());
        ((RandomView)view).setLives(lives);
    }

    @Override
    public void onFragmentViewCreated(GameFragment f) {
        view.setTitle(R.string.take_a_look);
        f.showAllSymbols();
        RunnableUtils.runDelayed(() -> {
            view.setTitle(games.get(currentGame).getTitle());
            f.coverAllSymbols();
        }, EXPOSURE_DURATION);
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameComplete() {
        super.onGameComplete();

        if (getCompletedGamesCount() == games.size()) {
            addResultFragment();
        } else {
            currentGame++;
            lives = DEFAULT_LIVES;
            RunnableUtils.runDelayed(() -> addGameFragment(), 200);
        }
    }

    @NonNull
    @Override
    protected ScoreRepository getScoreRepo() {
        return new RandomScores();
    }

    @Override
    public void onGameMatchFail() {
        if(--lives == 0){
            addResultFragment();
        }
        ((RandomView)view).setLives(lives);
    }
}
