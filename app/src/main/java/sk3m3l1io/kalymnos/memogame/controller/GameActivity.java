package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.GameScreenView;
import sk3m3l1io.kalymnos.memogame.view.GameScreenViewImp;

public class GameActivity extends AppCompatActivity implements GameScreenView.SymbolClickListener {
    private GameScreenView view;

    private Game game;
    private int pairsFound = 0;
    private int previousClickedSymbolPosition = -1;
    private boolean[] symbolsClickState;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        symbolsClickState = new boolean[GameScreenView.SYMBOL_COUNT];
        game = getIntent().getParcelableExtra(Game.class.getSimpleName());
        ArrayUtils.shuffle(game.getSymbols());
        setupView();
    }

    private void setupView() {
        view = new GameScreenViewImp(getLayoutInflater(), null);
        view.setSymbolClickListener(this);
        view.setCover(game.getCover());
        setContentView(view.getRootView());
    }

    @Override
    public void onSymbolClick(int position) {
        boolean isClicked = symbolsClickState[position];
        if (isClicked) {
            view.setSymbol(position, game.getCover());
            symbolsClickState[position] = false;
        } else {
            String symbol = game.getSymbols()[position];
            view.setSymbol(position, symbol);
            symbolsClickState[position] = true;
        }
    }
}
