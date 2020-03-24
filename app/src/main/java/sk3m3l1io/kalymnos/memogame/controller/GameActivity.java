package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.GameProcedure;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.GameScreen;
import sk3m3l1io.kalymnos.memogame.view.GameScreenImp;

public class GameActivity extends AppCompatActivity implements
        GameScreen.SymbolClickListener,
        GameProcedure.GameTimeListener {

    private Game game;
    private GameProcedure gameProcedure;
    private GameScreen view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameProcedure = new GameProcedure(GameScreen.SYMBOL_COUNT);
        gameProcedure.setGameTimeListener(this);
        game = getIntent().getParcelableExtra(Game.class.getSimpleName());
        ArrayUtils.shuffle(game.getSymbols());
        setupView();
    }

    private void setupView() {
        view = new GameScreenImp(getLayoutInflater(), null);
        view.setSymbolClickListener(this);
        view.setTitle(game.getTitle());
        view.setCover(game.getCover());
        setContentView(view.getRootView());
    }

    @Override
    public void onSymbolClick(int position) {
        if (gameProcedure.isSymbolClicked(position)) {
            view.setSymbol(position, game.getCover());
            gameProcedure.setSymbolNotClicked(position);
        } else {
            String symbol = game.getSymbols()[position];
            view.setSymbol(position, symbol);
            gameProcedure.setSymbolClicked(position);
        }
    }

    @Override
    public void onGameTimeStart() {
        Toast.makeText(this, "Game started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGameTimeTick(int elapsedSeconds) {
        view.setTime("" + elapsedSeconds);
    }

    @Override
    public void onGameTimeStop() {
        view.setTime("" + 0);
    }
}
