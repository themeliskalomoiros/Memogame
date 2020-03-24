package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.view.MainScreenView;
import sk3m3l1io.kalymnos.memogame.view.MainScreenViewImp;

public class MainActivity extends AppCompatActivity implements MainScreenView.PlayClickListener {
    private MainScreenView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainScreenViewImp(getLayoutInflater(), null);
        view.setPlayClickListener(this);
        setContentView(view.getRootView());
    }

    @Override
    public void onPlayClick() {
        Intent i = new Intent(this, GameActivity.class);
        Game game = new Game(
                "Test Game",
                "⛶",
                new String[]{"⛺", "⛱", "⛲", "⛴", "⛵", "⛭", "⛺", "⛱", "⛲", "⛴", "⛵", "⛭"});
        i.putExtra(Game.class.getSimpleName(), game);
        startActivity(i);
    }
}
