package sk3m3l1io.duisburg.memogame.controller;

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

public class PractiseModeActivity extends GameContainerActivity implements
        GameFragment.GameEventListener,
        MessageDialog.ResponseListener,
        PractiseView.ChangeGameClickListener {
    private static final String REPEAT_DIALOG = "repeat dialog";
    private static final String NEXT_GAME_DIALOG = "move to next dialog";

    @Override
    protected void initView() {
        view = new PractiseViewImp(getLayoutInflater(), null);
        ((PractiseView) view).setChangeGameClickListener(this);
    }

    @Override
    protected void arrangeGameSequence() {
        Collections.sort(games, (g1, g2) -> g1.getTitle().compareTo(g2.getTitle()));
    }

    @Override
    protected void onAttachGameFragment(GameFragment f) {
        view.setTitle(games.get(currentGame).getTitle());
        view.setDifficulty(games.get(currentGame).getDifficulty());
    }

    @Override
    public void onPreviousGameClick() {
        setGameIndexOnPreviousClick();
        addGameFragment();
    }

    private void setGameIndexOnPreviousClick() {
        if (currentGame > 0) {
            --currentGame;
        } else{
            currentGame = games.size() - 1;
        }
    }

    @Override
    public void onNextGameClick() {
        setIndexOnNextClick();
        addGameFragment();
    }

    private void setIndexOnNextClick() {
        if (currentGame < games.size() - 1) {
            ++currentGame;
        } else {
            currentGame = 0;
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

    @Override
    public void onGameStart() {
    }

    @Override
    public void onGameMatchFail(){
    }

    @Override
    public void onGameComplete() {
        view.setTitle(getString(R.string.victory));
        MessageDialog.show(
                this,
                getSupportFragmentManager(),
                getString(R.string.next_game),
                NEXT_GAME_DIALOG);
    }
}
