package sk3m3l1io.duisburg.memogame.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.game_engine.GameState;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.view.game.GameView;
import sk3m3l1io.duisburg.memogame.view.game.GameViewImpl;

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

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new GameViewImpl(inflater, container);
        view.setSymbolClickListener(this);
        return view.getRootView();
    }
}
