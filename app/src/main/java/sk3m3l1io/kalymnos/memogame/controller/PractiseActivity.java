package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.dialogs.NextGameDialog;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.GameProcedure;
import sk3m3l1io.kalymnos.memogame.view.GameScreen;
import sk3m3l1io.kalymnos.memogame.view.GameScreenImp;

public class PractiseActivity extends AppCompatActivity implements
        GameScreen.ClickListener,
        GameProcedure.TimeListener,
        GameProcedure.PairMatchListener,
        GameProcedure.ResultListener,
        NextGameDialog.ResponseListener {
    private static final int GAME_DURATION = 20000;

    private boolean gameBegun;
    private int currentGame;
    private List<Game> games;
    private GameProcedure gameProcedure;

    private GameScreen view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        updateUI();
    }

    private void init() {
        gameProcedure = new GameProcedure(GameScreen.SYMBOL_COUNT, GAME_DURATION);
        gameProcedure.setTimeListener(this);
        gameProcedure.setPairMatchListener(this);
        gameProcedure.setResultListener(this);
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        view = new GameScreenImp(getLayoutInflater(), null);
        view.setClickListener(this);
        setContentView(view.getRootView());
    }

    private void updateUI() {
        Game game = games.get(currentGame);
        view.setTitle(game.getTitle());
        view.setAllSymbolsValue(game.getCover());
        int color = getResources().getColor(R.color.primaryColor);
        view.setAllSymbolsColor(color);
        view.setAllSymbolsBackgroundToDefault();
        view.setTimeMaxProgress(gameProcedure.getDuration());
        view.setTimeProgress(gameProcedure.getDuration());
        view.enableAllSymbols();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameProcedure.stop();
        gameProcedure.detachListeners();
    }

    @Override
    public void onPreviousClick() {
        if (currentGame > 0) {
            --currentGame;
            gameProcedure.stop();
            gameProcedure.resetState();
            gameBegun = false;
            updateUI();
        }
    }

    @Override
    public void onNextClick() {
        if (currentGame < games.size() - 1) {
            ++currentGame;
            gameProcedure.stop();
            gameProcedure.resetState();
            gameBegun = false;
            updateUI();
        }
    }

    @Override
    public void onSymbolClick(int pos) {
        if (!gameBegun)
            gameProcedure.begin();

        int color = getResources().getColor(R.color.secondaryColor);
        view.setSymbolColor(pos, color);
        String symbol = games.get(currentGame).getSymbols()[pos];
        view.setSymbolValue(pos, symbol);

        gameProcedure.addTappedSymbol(pos, symbol);
    }

    @Override
    public void onGameTimeBegin() {
        gameBegun = true;
    }

    @Override
    public void onGameTimeProgress(int elapsedMilli) {
        view.setTimeProgress(elapsedMilli);
    }

    @Override
    public void onGameTimeFinish() {
        view.disableAllSymbols();
        view.setTimeProgress(0);
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
        int symbolColor = getResources().getColor(R.color.symbolMatchColor);
        view.setSymbolColor(position1, symbolColor);
        view.setSymbolColor(position2, symbolColor);
    }

    @Override
    public void onPairMatchFail(int position1, int position2) {
        Runnable setSymbolValues = () -> {
            int symbolColor = getResources().getColor(R.color.primaryColor);
            view.setSymbolColor(position1, symbolColor);
            view.setSymbolColor(position2, symbolColor);

            Game game = games.get(currentGame);
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
    }

    private void showNextGameDialog(int messageRes) {
        NextGameDialog d = new NextGameDialog();
        d.setMessageRes(messageRes);
        d.setResponseListener(this);
        showDialogAllowingStateLoss(getSupportFragmentManager(), d, null);
    }

    // From https://medium.com/inloopx/demystifying-androids-commitallowingstateloss-cb9011a544cc
    public static void showDialogAllowingStateLoss(
            FragmentManager fragmentManager,
            DialogFragment dialogFragment,
            String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(dialogFragment, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onDialogPositiveResponse(NextGameDialog dialog) {
        dialog.dismiss();
        onNextClick();
    }

    @Override
    public void onDialogNegativeResponse(NextGameDialog dialog) {
        finish();
    }
}
