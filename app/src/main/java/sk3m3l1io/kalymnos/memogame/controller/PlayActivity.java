package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.view.PlayScreenView;
import sk3m3l1io.kalymnos.memogame.view.PlayScreenViewImp;

public class PlayActivity extends AppCompatActivity implements PlayScreenView.SymbolClickListener {
    private PlayScreenView view;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new PlayScreenViewImp(getLayoutInflater(), null);
        view.setSymbolClickListener(this);
        setContentView(view.getRootView());
        game = new Game(
                "Test Game",
                '⛶',
                new char[]{'⛺', '⛱', '⛲', '⛴', '⛵', '⛭', '⛺', '⛱', '⛲', '⛴', '⛵', '⛭'});
    }

    @Override
    public void onSymbolClick(int position) {

    }
}
