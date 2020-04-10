package sk3m3l1io.duisburg.memogame.controller;

import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.game_engine.GameState;
import sk3m3l1io.duisburg.memogame.view.game.GameView;

public class GameEngineFragment extends Fragment
        implements GameView.SymbolClickListener,
        GameState.PairMatchListener,
        GameState.PairMatchCompletionListener,
        GameState.SymbolAlreadyUncoveredListener {
    private GameView view;

    @Override
    public void onSymbolClick(int pos) {

    }

    @Override
    public void onPairMatch(int position1, int position2) {

    }

    @Override
    public void onPairMatchFail(int position1, int position2) {

    }

    @Override
    public void onPairMatchesCompleted() {

    }

    @Override
    public void onSymbolAlreadyUncovered(int position) {

    }
}
