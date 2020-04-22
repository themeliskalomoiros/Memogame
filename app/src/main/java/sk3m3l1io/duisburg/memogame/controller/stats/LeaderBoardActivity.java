package sk3m3l1io.duisburg.memogame.controller.stats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardView;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity implements ScoresFragment.OnScoreDetailsClickListener{
    private Player user;
    private LeaderBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra(Player.class.getSimpleName());
        view = new LeaderBoardViewImp(getLayoutInflater(), null, getSupportFragmentManager());
        setContentView(view.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        addScoreFragment();
    }

    private void addScoreFragment() {
        ScoresFragment f = new ScoresFragment();
        f.setUser(user);
        f.setOnScoreDetailsClickListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(view.getFragmentContainerId(), f)
                .commit();
    }

    @Override
    public void onScoreDetailsClick(ScoreData sd, int rank) {
        addScoreDetailsFragment(sd, rank);
    }

    private void addScoreDetailsFragment(ScoreData sd, int rank) {
        ScoreDetailsFragment f = new ScoreDetailsFragment();
        f.setRank(rank);
        f.setScoreData(sd);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(view.getFragmentContainerId(), f)
                .commit();
    }
}
