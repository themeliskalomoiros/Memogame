package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.dialoges.NextGameDialog;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.GameProcedure;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
import sk3m3l1io.kalymnos.memogame.view.GameScreen;
import sk3m3l1io.kalymnos.memogame.view.GameScreenImp;

public class GameActivity extends AppCompatActivity implements
        GameScreen.ClickListener,
        GameProcedure.TimeListener,
        GameProcedure.PairMatchListener,
        GameProcedure.ResultListener,
        NextGameDialog.ResponseListener {

    private Game game;
    private List<Game> games;

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
        gameProcedure.setPairMatchListener(this);
        gameProcedure.setResultListener(this);
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        game = games.get(new Random().nextInt(games.size()));
        ArrayUtils.shuffle(game.getSymbols());
        view = new GameScreenImp(getLayoutInflater(), null);
        view.setClickListener(this);
    }

    private void setupUi() {
        view.setTitle(game.getTitle());
        view.setTimeProgressMax(GameProcedure.DURATION);
        view.setTimeProgress(GameProcedure.DURATION);
        view.coverAllSymbols(game.getCover());
        setContentView(view.getRootView());
    }

    @Override
    public void onNextClick() {
        gameProcedure.detachListeners();
        recreate();
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
        view.setTimeProgress(0);
        view.showNextButton();
        if (gameProcedure.gameWon()) {
            showNextGameDialog(R.string.victory);
        } else {
            showNextGameDialog(R.string.defeat);
        }
    }

    @Override
    public void onPairMatch(int position1, int position2) {
        view.disableSymbol(position1);
        view.disableSymbol(position2);
        int symbolColor = getResources().getColor(R.color.secondaryLightColor);
        view.setSymbolColor(position1, symbolColor);
        view.setSymbolColor(position2, symbolColor);
    }

    @Override
    public void onPairMatchFail(int position1, int position2) {
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
        setVictoryUi();
        showNextGameDialog(R.string.victory);
    }

    private void setVictoryUi() {
        int backgroundColor = getResources().getColor(R.color.primaryLightColor);
        view.setAllSymbolsBackgroundColor(backgroundColor);
        int symbolColor = getResources().getColor(R.color.secondaryColor);
        view.setAllSymbolsColor(symbolColor);
        view.showNextButton();
    }

    private void showNextGameDialog(int messageRes) {
        NextGameDialog d = new NextGameDialog(messageRes);
        d.setResponseListener(this);
        d.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onDialogPositiveResponse(NextGameDialog dialog) {
        gameProcedure.detachListeners();
        dialog.dismiss();
        dialog.setResponseListener(null);
        recreate();
    }

    @Override
    public void onDialogNegativeResponse(NextGameDialog dialog) {
        finish();
    }
}
