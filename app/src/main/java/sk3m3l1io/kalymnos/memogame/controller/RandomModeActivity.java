package sk3m3l1io.kalymnos.memogame.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import sk3m3l1io.kalymnos.memogame.view.game.RandomView;
import sk3m3l1io.kalymnos.memogame.view.game.RandomViewImp;

public class RandomModeActivity extends AppCompatActivity {
    private RandomView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new RandomViewImp(getLayoutInflater(), null);
        setContentView(view.getRootView());
    }
}
