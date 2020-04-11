package sk3m3l1io.duisburg.memogame.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.view.GameContainerView;

public abstract class GameActivity extends AppCompatActivity
        implements GameFragment.GameEventListener {

    protected int currentGame;
    protected List<Game> games;
    protected GameContainerView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        initView();
        arrangeGameSequence();
        addGameFragment();
        setContentView(view.getRootView());
    }

    protected abstract void initView();

    protected abstract void arrangeGameSequence();

    protected void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(view.getGameContainerId(), getCurrentGameFragment())
                .commit();
    }

    private GameFragment getCurrentGameFragment(){
        Game g = games.get(currentGame);
        ArrayUtils.shuffle(g.getSymbols());
        GameFragment f = new GameFragment();
        // TODO: RandomActivity needs set ViewCreationListener, so override there
        f.setGame(g);
        return f;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment f) {
        super.onAttachFragment(f);
        if (f instanceof GameFragment) {
            onAttachGameFragment((GameFragment) f);
        }
    }

    protected abstract void onAttachGameFragment(GameFragment f);
}
