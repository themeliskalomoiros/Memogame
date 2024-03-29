package sk3m3l1io.duisburg.memogame.controller.game;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Game;
import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.view.GameContainerView;

public abstract class GameActivity extends AppCompatActivity
        implements GameFragment.GameEventListener {

    protected int currentGame;
    protected List<Game> games;
    protected GameMode gameMode;
    protected GameContainerView view;
    private MediaPlayer backwardSound;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        gameMode = (GameMode) getIntent().getSerializableExtra(GameMode.class.getSimpleName());
        initView();
        arrangeGameSequence();
        addGameFragment();
        setContentView(view.getRootView());
    }

    protected abstract void initView();

    protected abstract void arrangeGameSequence();

    protected final void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(view.getGameContainerId(), getCurrentGameFragment())
                .commit();
    }

    private GameFragment getCurrentGameFragment() {
        Game g = games.get(currentGame);
        ArrayUtils.shuffle(g.getSymbols());
        GameFragment f = new GameFragment();
        f.setViewCreationListener(getGameFragmentViewCreationListener());
        f.setGame(g);
        return f;
    }

    protected abstract GameFragment.ViewCreationListener getGameFragmentViewCreationListener();

    @Override
    public final void onAttachFragment(@NonNull Fragment f) {
        super.onAttachFragment(f);
        if (f instanceof GameFragment) {
            onAttachGameFragment((GameFragment) f);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        backwardSound = MediaPlayer.create(this, R.raw.navigation_backward_minimal);
    }

    @Override
    public final void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onStop() {
        super.onStop();
        backwardSound.release();
    }

    protected abstract void onAttachGameFragment(GameFragment f);

    @Override
    public final void onBackPressed() {
        backwardSound.start();
        super.onBackPressed();
    }

    public static Intent createIntent(Context c, GameMode mode, Bundle data) {
        Intent i = new Intent();
        i.putExtras(data);
        switch (mode) {
            case TIME:
                i.setClass(c, TimeModeActivity.class);
                break;
            case PRACTISE:
                i.setClass(c, PractiseModeActivity.class);
                break;
            case SURVIVAL:
                i.setClass(c, SurvivalModeActivity.class);
                break;
        }

        return i;
    }
}
