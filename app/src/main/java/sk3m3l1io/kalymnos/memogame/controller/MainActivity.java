package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.view.MainScreenView;
import sk3m3l1io.kalymnos.memogame.view.MainScreenViewImp;

public class MainActivity extends AppCompatActivity implements MainScreenView.PlayActionListener {
    private MainScreenView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainScreenViewImp(getLayoutInflater(), null);
        view.setPlayActionListener(this);
        setContentView(view.getRootView());
    }

    @Override
    public void onPlayActionInvoked() {
        Intent i = new Intent(this, PlayActivity.class);
        startActivity(i);
    }
}
