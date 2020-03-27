package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import sk3m3l1io.kalymnos.memogame.view.score.LeaderBoardView;
import sk3m3l1io.kalymnos.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity implements
        ScoresFragment.ScoresLoadListener {
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new LeaderBoardViewImp(getLayoutInflater(), null);
        setContentView(view.getRootView());
        addScoreFragment();
    }

    private void addScoreFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getFragmentContainerId(), new ScoresFragment())
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
