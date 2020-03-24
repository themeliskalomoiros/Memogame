package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.GameProcedure;
import sk3m3l1io.kalymnos.memogame.view.GameScreen;
import sk3m3l1io.kalymnos.memogame.view.GameScreenImp;

public class GameActivity extends AppCompatActivity implements
        GameScreen.SymbolClickListener,
        GameProcedure.TimeListener,
        GameProcedure.MatchListener,
        GameProcedure.ResultListener {

    private Game game;
    private GameProcedure gameProcedure;
    private GameScreen view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setupUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameProcedure.begin();
    }

    private void init() {
        gameProcedure = new GameProcedure(GameScreen.SYMBOL_COUNT);
        gameProcedure.setTimeListener(this);
        gameProcedure.setSymbolListener(this);
        gameProcedure.setResultListener(this);
        game = getIntent().getParcelableExtra(Game.class.getSimpleName());
        view = new GameScreenImp(getLayoutInflater(), null);
        view.setSymbolClickListener(this);
    }

    private void setupUi() {
        view.setTitle(game.getTitle());
        view.setTimeProgressMax(GameProcedure.DURATION);
        view.setTimeProgress(GameProcedure.DURATION);
        view.coverAllSymbols(game.getCover());
        setContentView(view.getRootView());
    }

    @Override
    public void onSymbolClick(int position) {
        String value = game.getSymbols()[position];
        view.setSymbolValue(position, value);
        gameProcedure.putClickedSymbol(position, value);
    }

    @Override
    public void onGameTimeBegin() {
        Toast.makeText(this, "Game started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGameTimeProgress(int elapsedMilli) {
        view.setTimeProgress(elapsedMilli);
    }

    @Override
    public void onGameTimeFinish() {
        view.disableAllSymbols();
        view.setTimeProgressMax(0);
    }

    @Override
    public void onSymbolMatch(int position1, int position2) {
        view.disableSymbol(position1);
        view.disableSymbol(position2);
        int colorRes = getResources().getColor(R.color.secondaryDarkColor);
        view.setSymbolBackground(position1, colorRes);
        view.setSymbolBackground(position2, colorRes);
    }

    @Override
    public void onSymbolMatchFail(int position1, int position2) {
        Runnable setSymbolValues = () -> {
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
    public void onGameWon() {
        gameProcedure.stop();
        int colorRes = getResources().getColor(R.color.secondaryLightColor);
        view.setAllSymbolsBackground(colorRes);
        Toast.makeText(this, "Victory!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGameLost() {

    }
}
