package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
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
        view.setAllSymbolsValue(g.getCover());
        setContentView(view.getRootView());
    }

    @Override
    public void onPreviousClick() {

    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void onSymbolClick(int pos) {

    }
}
