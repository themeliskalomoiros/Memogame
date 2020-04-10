package sk3m3l1io.duisburg.memogame.controller;

import android.os.Bundle;
import android.util.Log;
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

// TODO: Don't forget to clear resources, detach listeners, etc...
public class GameEngineFragment extends Fragment
        implements GameView.SymbolClickListener,
        GameState.PairMatchListener,
        GameState.PairMatchCompletionListener,
        GameState.SymbolAlreadyUncoveredListener {

    private GameView view;
    private GameState gameState;

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
        view.coverAllSymbols("" + gameState.getCover());
        return view.getRootView();
    }

    public void set(Game game){
        initGameStateOf(game);
    }

    private void initGameStateOf(Game game) {
        try {
            char cover = game.getCover().charAt(0);
            gameState = new GameState(createSymbolCharactersFrom(game), cover);
        } catch (Exception e) {
            Log.e(GameEngineFragment.class.getSimpleName(), e.getMessage());
        }
    }

    private char[] createSymbolCharactersFrom(Game game) {
        char[] symbols = new char[game.getSymbols().length];
        for (int i = 0; i < symbols.length; i++) {
            char s = game.getSymbols()[i].charAt(0);
            symbols[i] = s;
        }
        return symbols;
    }
}
