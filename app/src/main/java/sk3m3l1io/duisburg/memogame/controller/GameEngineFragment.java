package sk3m3l1io.duisburg.memogame.controller;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.game_engine.GameState;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.utils.RunnableUtils;
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
    private MediaPlayer matchCelebration, completionCelebration;

    @Override
    public void onSymbolClick(int position) {
        int color = getResources().getColor(R.color.secondaryColor);
        view.setSymbolForeground(position, color);
        String symbol = "" + gameState.getSymbolAt(position);
        view.setSymbol(position, symbol);
    }

    @Override
    public void onPairMatch(int position1, int position2) {
        view.disableSymbol(position1);
        view.disableSymbol(position2);
        int color = getResources().getColor(R.color.symbolMatchColor);
        view.setSymbolForeground(position1, color);
        view.setSymbolForeground(position2, color);
        matchCelebration.start();
    }

    @Override
    public void onPairMatchFail(int position1, int position2) {
        Runnable setSymbolValues = () -> {
            int color = getResources().getColor(R.color.primaryColor);
            view.setSymbolForeground(position1, color);
            view.setSymbolForeground(position2, color);
            String cover = "" + gameState.getCover();
            view.setSymbol(position1, cover);
            view.setSymbol(position2, cover);
        };
        RunnableUtils.runDelayed(setSymbolValues, 300);
    }

    @Override
    public void onPairMatchesCompleted() {
        view.disableAllSymbols();
        int color = getResources().getColor(R.color.primaryDarkColor);
        view.setAllSymbolsBackground(color);
        completionCelebration.start();
    }

    @Override
    public void onSymbolAlreadyUncovered(int position) {
        Toast.makeText(getContext(), "You already tapped that!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        matchCelebration = MediaPlayer.create(getContext(), R.raw.match_success);
        completionCelebration = MediaPlayer.create(getContext(), R.raw.game_success);
    }

    @Override
    public void onStop() {
        super.onStop();
        matchCelebration.release();
        completionCelebration.release();
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
