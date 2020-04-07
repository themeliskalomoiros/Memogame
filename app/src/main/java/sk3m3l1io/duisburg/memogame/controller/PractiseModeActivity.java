package sk3m3l1io.duisburg.memogame.controller;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.dialogs.MessageDialog;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.utils.ArrayUtils;
import sk3m3l1io.duisburg.memogame.view.game.PractiseView;
import sk3m3l1io.duisburg.memogame.view.game.PractiseViewImp;

public class PractiseModeActivity extends AppCompatActivity implements
        GameFragment.GameProgressListener,
        MessageDialog.ResponseListener,
        PractiseView.ChangeGameClickListener {
    private static final String REPEAT_DIALOG = "repeat dialog";
    private static final String NEXT_GAME_DIALOG = "move to next dialog";

    private int currentGame;
    private List<Game> games;
    private PractiseView view;
    private MediaPlayer successSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        Collections.sort(games, (g1, g2) -> g1.getTitle().compareTo(g2.getTitle()));
        view = new PractiseViewImp(getLayoutInflater(), null);
        view.setChangeGameClickListener(this);
        setContentView(view.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        addGameFragment();
        successSound = MediaPlayer.create(this, R.raw.game_success);
    }

    private void addGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        successSound.release();
    }

    @Override
    public void onPreviousGameClick() {
        if (currentGame > 0) {
            --currentGame;
        }else{
            currentGame = games.size() -1;
        }

        addGameFragment();
    }

    @Override
    public void onNextGameClick() {
        if (currentGame < games.size() - 1) {
            ++currentGame;
        }else{
            currentGame = 0;
        }

        addGameFragment();
    }

    @Override
    public void onGameBegin() {
    }

    @Override
    public void onGameCompleted() {
        view.setTitle(getString(R.string.victory));
        successSound.start();
        MessageDialog.show(
                this,
                getSupportFragmentManager(),
                getString(R.string.next_game),
                NEXT_GAME_DIALOG);
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
