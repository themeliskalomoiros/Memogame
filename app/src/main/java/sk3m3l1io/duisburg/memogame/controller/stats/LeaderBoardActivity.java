package sk3m3l1io.duisburg.memogame.controller.stats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardView;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity implements
        ScoresFragment.ScoresLoadListener,
        LeaderBoardView.PageChangeListener {
    private Player user;
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra(Player.class.getSimpleName());
        view = new LeaderBoardViewImp(getLayoutInflater(), null, getSupportFragmentManager());
        view.setScorePageChangeListener(this);
        view.setUser(user);
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
    public void onTimeModePage() {
        view.setTitle(R.string.time_mode);
    }

    @Override
    public void onSurvivalModePage() {
        view.setTitle(R.string.survival_mode);
    }
}
