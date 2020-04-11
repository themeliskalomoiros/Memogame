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
import sk3m3l1io.duisburg.memogame.game_engine.InvalidCoverException;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;
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
    private GameProcedureListener gameProcedureListener;
    private MediaPlayer matchCelebration, completionCelebration;

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
        initGameStateFrom(game);
    }

    private void initGameStateFrom(Game g) {
        try {
            gameState = new GameState(g.getSymbols(), g.getCover());
            gameState.setSymbolAlreadyUncoveredListener(this);
            gameState.setGameBeginListener(this);
            gameState.setGameCompletionListener(this);
            gameState.setMatchListener(this);
        } catch (InvalidCoverException e) {
            String msg = e.getMessage() + "(on game " + g.getTitle() + ")";
            Log.e(LogUtils.TAG, msg);
            e.printStackTrace();
        }
    }

    @Override
    public void onGameBegin() {
        gameProcedureListener.onGameStart();
    }

    @Override
    public void onSymbolClick(int position) {
        updateUiOnSymbolClick(position);
        gameState.uncover(position);
    }

    private void updateUiOnSymbolClick(int pos) {
        int clr = getResources().getColor(R.color.secondaryColor);
        view.setSymbolForeground(pos, clr);
        view.setSymbol(pos, gameState.getSymbolAt(pos));
    }

    @Override
    public void onSymbolAlreadyUncovered(int position) {
        Toast.makeText(getContext(), "You already tapped that!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMatch(int position1, int position2) {
        updateUiOnMatch(position1, position2);
        matchCelebration.start();
    }

    private void updateUiOnMatch(int pos1, int pos2) {
        view.disableSymbol(pos1);
        view.disableSymbol(pos2);
        int clr = getResources().getColor(R.color.symbolMatchColor);
        view.setSymbolForeground(pos1, clr);
        view.setSymbolForeground(pos2, clr);
    }

    @Override
    public void onMatchFail(int position1, int position2) {
        RunnableUtils.runDelayed(() -> updateUiOnMatchFail(position1, position2), 300);
    }

    private void updateUiOnMatchFail(int pos1, int pos2) {
        int clr = getResources().getColor(R.color.primaryColor);
        view.setSymbolForeground(pos1, clr);
        view.setSymbolForeground(pos2, clr);
        view.setSymbol(pos1, gameState.getCover());
        view.setSymbol(pos2, gameState.getCover());
    }

    @Override
    public void onGameCompleted() {
        updateUiOnGameCompleted();
        completionCelebration.start();
        gameProcedureListener.onGameComplete();
    }

    private void updateUiOnGameCompleted() {
        view.disableAllSymbols();
        int clr = getResources().getColor(R.color.primaryDarkColor);
        view.setAllSymbolsBackground(clr);
    }

    public interface GameProcedureListener {
        void onGameStart();

        void onGameComplete();
    }
}
