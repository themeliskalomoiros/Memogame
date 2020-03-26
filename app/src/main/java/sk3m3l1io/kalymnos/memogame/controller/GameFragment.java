package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.GameProcedure;
import sk3m3l1io.kalymnos.memogame.view.GameView;
import sk3m3l1io.kalymnos.memogame.view.GameViewImpl;

public class GameFragment extends Fragment implements
        GameView.SymbolClickListener,
        GameProcedure.MatchListener,
        GameProcedure.CompletionListener {
    private Game game;
    private boolean gameBegun;
    private GameView view;
    private GameProcedure gameProcedure;
    private GameProgressListener gameProgressListener;

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
        view.setAllSymbolsValue(game.getCover());
        return view.getRootView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameProgressListener = null;
        gameProcedure.detachListeners();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void onMatch(int pos1, int pos2) {
        view.disableSymbol(pos1);
        view.disableSymbol(pos2);
        int symbolColor = getResources().getColor(R.color.symbolMatchColor);
        view.setSymbolColor(pos1, symbolColor);
        view.setSymbolColor(pos2, symbolColor);
    }

    @Override
    public void onMatchFailure(int position1, int position2) {
        Runnable setSymbolValues = () -> {
            int symbolColor = getResources().getColor(R.color.primaryColor);
            view.setSymbolColor(position1, symbolColor);
            view.setSymbolColor(position2, symbolColor);
            view.setSymbolValue(position1, game.getCover());
            view.setSymbolValue(position2, game.getCover());
        };
        runWithDelay(setSymbolValues, 300);
    }

    public void runWithDelay(Runnable runnable, int delayMillis) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            runnable.run();
        }, delayMillis);
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
        view.setSymbolColor(pos, color);
        view.setSymbolValue(pos, symbol);
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
        view.setAllSymbolsBackgroundColor(color);
    }

    public interface GameProgressListener {
        void onGameBegin();

        void onGameCompleted();
    }
}
