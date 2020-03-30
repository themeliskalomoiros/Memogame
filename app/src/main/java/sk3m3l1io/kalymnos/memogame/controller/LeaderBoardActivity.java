package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.view.score.LeaderBoardView;
import sk3m3l1io.kalymnos.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity
        implements ScoresFragment.ScoresLoadListener,
        LeaderBoardView.PageChangeListener {
    private Player user;
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra(Player.class.getSimpleName());
        view = new LeaderBoardViewImp(getLayoutInflater(), null, getSupportFragmentManager());
        view.setScorePageChangeListener(this);
        view.set(user);
        setContentView(view.getRootView());
    }

    @Override
    public void onLoadBegun() {
        view.showLoadingIndicator();
    }

    @Override
    public void onLoadFinished() {
        view.hideLoadingIndicator();
    }

    @Override
    public void onLightningPage() {
        view.setTitle(R.string.lightning_round);
    }

    @Override
    public void onRandomPage() {
        view.setTitle(R.string.random);
    }
}
