package sk3m3l1io.duisburg.memogame.controller.stats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardView;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity {
    private Player user;
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra(Player.class.getSimpleName());
        view = new LeaderBoardViewImp(getLayoutInflater(), null, getSupportFragmentManager());
        setContentView(view.getRootView());
        addScoreFragment();
    }

    private void addScoreFragment() {
        ScoreDataFragment f = new ScoreDataFragment();
        f.setUser(user);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(view.getFragmentContainerId(), f);
    }
}
