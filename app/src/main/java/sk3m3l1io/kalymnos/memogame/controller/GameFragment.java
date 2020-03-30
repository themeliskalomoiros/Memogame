package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.GameProcedure;
import sk3m3l1io.kalymnos.memogame.utils.RunnableUtils;
import sk3m3l1io.kalymnos.memogame.view.game.GameView;
import sk3m3l1io.kalymnos.memogame.view.game.GameViewImpl;

public class GameFragment extends Fragment implements
        GameView.SymbolClickListener,
        GameProcedure.MatchListener,
        GameProcedure.CompletionListener {
    private Game game;
    private boolean gameBegun;
    private GameProcedure gameProcedure;
    private GameProgressListener gameProgressListener;

    private GameView view;
    private MediaPlayer celebrationSound;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            gameProgressListener = (GameProgressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + GameProgressListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameProcedure = new GameProcedure(GameView.SYMBOL_COUNT);
        gameProcedure.setMatchListener(this);
        gameProcedure.setCompletionListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new GameViewImpl(inflater, container);
        view.setSymbolClickListener(this);
        view.coverAllSymbols(game.getCover());
        return view.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        celebrationSound = MediaPlayer.create(getContext(), R.raw.hero_celebration);
    }

    @Override
    public void onStop() {
        super.onStop();
        celebrationSound.release();
        celebrationSound = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.setSymbolClickListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameProcedure.detachListeners();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        gameProgressListener = null;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void onMatch(int pos1, int pos2) {
        updateMatchUI(pos1, pos2);
        celebrationSound.start();
    }

    private void updateMatchUI(int symbolPos1, int symbolPos2) {
        view.disableSymbol(symbolPos1);
        view.disableSymbol(symbolPos2);
        int symbolColor = getResources().getColor(R.color.symbolMatchColor);
        view.setSymbolForeground(symbolPos1, symbolColor);
        view.setSymbolForeground(symbolPos2, symbolColor);
    }

    @Override
    public void onMatchFailure(int position1, int position2) {
        Runnable setSymbolValues = () -> {
            int symbolColor = getResources().getColor(R.color.primaryColor);
            view.setSymbolForeground(position1, symbolColor);
            view.setSymbolForeground(position2, symbolColor);
            view.setSymbol(position1, game.getCover());
            view.setSymbol(position2, game.getCover());
        };
        RunnableUtils.runDelayed(setSymbolValues, 300);
    }

    @Override
    public void onGameCompleted() {
        freezeUI();
        gameProgressListener.onGameCompleted();
    }

    @Override
    public void onSymbolClick(int pos) {
        reportBegin();
        String symbol = game.getSymbols()[pos];
        updateSymbolUI(pos, symbol);
        gameProcedure.addTappedSymbol(pos, symbol);
    }

    private void updateSymbolUI(int pos, String symbol) {
        int color = getResources().getColor(R.color.secondaryColor);
        view.setSymbolForeground(pos, color);
        view.setSymbol(pos, symbol);
    }

    private void reportBegin() {
        if (!gameBegun) {
            gameBegun = true;
            gameProgressListener.onGameBegin();
        }
    }

    public void freezeUI() {
        view.disableAllSymbols();
        int color = getResources().getColor(R.color.primaryDarkColor);
        view.setAllSymbolsBackground(color);
    }

    public interface GameProgressListener {
        void onGameBegin();

        void onGameCompleted();
    }
}
