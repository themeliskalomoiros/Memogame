package sk3m3l1io.duisburg.memogame.controller;

import android.content.Context;
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

public class GameEngineFragment extends Fragment
        implements GameView.SymbolClickListener,
        GameState.MatchListener,
        GameState.GameBeginListener,
        GameState.GameCompletionListener,
        GameState.SymbolAlreadyUncoveredListener {

    private GameView view;
    private GameState gameState;
    private MediaPlayer matchCelebration, completionCelebration;
    private GameProcedureListener gameProcedureListener;

    @Override
    public void onSymbolClick(int position) {
        updateUiOnSymbolClick(position);
        gameState.uncover(position);
    }

    private void updateUiOnSymbolClick(int s) {
        int c = getResources().getColor(R.color.secondaryColor);
        view.setSymbolForeground(s, c);
        String symbol = "" + gameState.getSymbolAt(s);
        view.setSymbol(s, symbol);
    }

    @Override
    public void onMatch(int position1, int position2) {
        updateUiOnMatch(position1, position2);
        matchCelebration.start();
    }

    private void updateUiOnMatch(int s1, int s2) {
        view.disableSymbol(s1);
        view.disableSymbol(s2);
        int c = getResources().getColor(R.color.symbolMatchColor);
        view.setSymbolForeground(s1, c);
        view.setSymbolForeground(s2, c);
    }

    @Override
    public void onMatchFail(int position1, int position2) {
        updateUiOnMatchFail(position1, position2);
    }

    private void updateUiOnMatchFail(int position1, int position2) {
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
    public void onGameBegin() {
        gameProcedureListener.onGameStart();
    }

    @Override
    public void onGameCompleted() {
        updateUiOnGameCompleted();
        completionCelebration.start();
        gameProcedureListener.onGameComplete();
    }

    private void updateUiOnGameCompleted() {
        view.disableAllSymbols();
        int color = getResources().getColor(R.color.primaryDarkColor);
        view.setAllSymbolsBackground(color);
    }

    @Override
    public void onSymbolAlreadyUncovered(int position) {
        Toast.makeText(getContext(), "You already tapped that!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            gameProcedureListener = (GameProcedureListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + GameProcedureListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new GameViewImpl(inflater, container);
        view.setSymbolClickListener(this);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gameProcedureListener = null;
        view.setSymbolClickListener(null);
        gameState.detachListeners();
    }

    public void setGame(Game game){
        initGameStateOf(game);
    }

    private void initGameStateOf(Game game) {
        try {
            char cover = game.getCover().charAt(0);
            gameState = new GameState(createSymbolCharactersFrom(game), cover);
            gameState.setSymbolAlreadyUncoveredListener(this);
            gameState.setGameBeginListener(this);
            gameState.setGameCompletionListener(this);
            gameState.setMatchListener(this);
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

    public interface GameProcedureListener {
        void onGameStart();

        void onGameComplete();
    }
}
