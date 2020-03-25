package sk3m3l1io.kalymnos.memogame.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.services.HotRoundGameProcedure;
import sk3m3l1io.kalymnos.memogame.view.GameScreen;
import sk3m3l1io.kalymnos.memogame.view.GameScreenImp;

public class HotRoundActivity extends AppCompatActivity implements GameScreen.ClickListener {

    private int currentGame = 0;
    private List<Game> games;

    private GameScreen view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setupUi();
    }

    private void init() {
        games = getIntent().getParcelableArrayListExtra(Game.class.getSimpleName());
        Collections.shuffle(games);
        view = new GameScreenImp(getLayoutInflater(), null);
        view.setClickListener(this);
    }

    private void setupUi() {
        Game g = games.get(currentGame);
        view.setTitle(g.getTitle());
        view.setTimeProgressMax(HotRoundGameProcedure.DURATION);
        view.setTimeProgress(HotRoundGameProcedure.DURATION);
        view.coverAllSymbols(g.getCover());
        setContentView(view.getRootView());
    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void onSymbolClick(int position) {

    }
}
