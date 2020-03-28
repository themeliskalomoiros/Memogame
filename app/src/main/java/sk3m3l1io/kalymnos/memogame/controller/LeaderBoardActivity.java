package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.view.score.LeaderBoardView;
import sk3m3l1io.kalymnos.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity implements
        ScoresFragment.ScoresLoadListener {
    private Player user;
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra(Player.class.getSimpleName());
        view = new LeaderBoardViewImp(getLayoutInflater(), null);
        setContentView(view.getRootView());
        addScoreFragment();
    }

    private void addScoreFragment() {
        ScoresFragment f = new ScoresFragment();
        f.setUser(user);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getFragmentContainerId(), f)
                .commit();
    }

    @Override
    public void onLoadBegun() {
        view.showLoadingIndicator();
    }

    @Override
    public void onLoadFinished() {
        view.hideLoadingIndicator();
    }
}
