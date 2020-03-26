package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.view.LeaderBoardView;
import sk3m3l1io.kalymnos.memogame.view.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity {
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new LeaderBoardViewImp(getLayoutInflater(), null);
        setContentView(view.getRootView());
    }
}
