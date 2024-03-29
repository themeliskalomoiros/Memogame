package sk3m3l1io.duisburg.memogame.controller.game;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Game;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
import sk3m3l1io.duisburg.memogame.view.game.SurvivalModeView;
import sk3m3l1io.duisburg.memogame.view.game.SurvivalModeViewImp;

public class SurvivalModeActivity extends ScoreActivity implements
        GameFragment.GameEventListener,
        GameFragment.ViewCreationListener {
    private static final int DEFAULT_LIVES = 4;
    private static final int EXPOSURE_DURATION = 3000;

    private int lives = DEFAULT_LIVES;

    @Override
    protected void initView() {
        view = new SurvivalModeViewImp(getLayoutInflater(), null);
    }

    @Override
    protected GameFragment.ViewCreationListener getGameFragmentViewCreationListener() {
        return this;
    }

    @Override
    protected void onAttachGameFragment(GameFragment f) {
        if (currentGame < games.size() - 1) {
            updateUiOnFragmentAttach();
        }
    }

    private void updateUiOnFragmentAttach() {
        Game g = games.get(currentGame);
        view.setDifficulty(g.getDifficulty());
        view.setTitle(g.getTitle());
        ((SurvivalModeView) view).setLives(lives);
    }

    @Override
    public void onFragmentViewCreated(GameFragment f) {
        view.setTitle(R.string.look);
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

    @Override
    public void onGameMatch() {
        super.onGameMatch();
    }

    @Override
    public void onGameMatchFail() {
        super.onGameMatchFail();
        if (--lives == 0) {
            addResultFragment();
        }
        ((SurvivalModeView) view).setLives(lives);
    }
}
