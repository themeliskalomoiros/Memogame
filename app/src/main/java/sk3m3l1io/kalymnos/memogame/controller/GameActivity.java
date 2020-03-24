package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.view.GameScreenView;
import sk3m3l1io.kalymnos.memogame.view.GameScreenViewImp;

public class GameActivity extends AppCompatActivity implements GameScreenView.SymbolClickListener {
    private Game game;
    private GameScreenView view;
    private boolean[] symbolsClickState = new boolean[GameScreenView.SYMBOL_COUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = getIntent().getParcelableExtra(Game.class.getSimpleName());
        shuffle(game.getSymbols());
        setupView();
    }

    private void setupView() {
        view = new GameScreenViewImp(getLayoutInflater(), null);
        view.setSymbolClickListener(this);
        setContentView(view.getRootView());
        view.setCover(game.getCover());
    }

    @Override
    public void onSymbolClick(int position) {
        boolean isClicked = symbolsClickState[position];
        if(isClicked){
            view.setSymbol(position, game.getCover());
            symbolsClickState[position] = false;
        }else{
            char symbol = game.getSymbols()[position];
            view.setSymbol(position, symbol);
            symbolsClickState[position] = true;
        }
    }

    static void shuffle(char[] chars)
    {
        Random rnd = new Random();
        for (int i = chars.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            char c = chars[index];
            chars[index] = chars[i];
            chars[i] = c;
        }
    }
}
