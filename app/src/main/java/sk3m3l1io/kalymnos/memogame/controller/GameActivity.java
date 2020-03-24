package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.view.GameScreenView;
import sk3m3l1io.kalymnos.memogame.view.GameScreenViewImp;

public class GameActivity extends AppCompatActivity implements GameScreenView.SymbolClickListener {
    private GameScreenView view;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameScreenViewImp(getLayoutInflater(), null);
        view.setSymbolClickListener(this);
        setContentView(view.getRootView());
        game = new Game(
                "Test Game",
                '⛶',
                new char[]{'⛺', '⛱', '⛲', '⛴', '⛵', '⛭', '⛺', '⛱', '⛲', '⛴', '⛵', '⛭'});
        view.setCover(game.getCover());
    }

    @Override
    public void onSymbolClick(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }
}
