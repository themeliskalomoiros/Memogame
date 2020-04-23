package sk3m3l1io.duisburg.memogame.controller.stats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.model.repos.FirebaseScoreRepository;
import sk3m3l1io.duisburg.memogame.model.repos.ScoreRepository;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardView;
import sk3m3l1io.duisburg.memogame.view.score.LeaderBoardViewImp;

public class LeaderBoardActivity extends AppCompatActivity implements
        ScoresFragment.OnScoreDetailsClickListener,
        ScoreRepository.ScoresListener {
    private Player user;
    private LeaderBoardView view;
    private ScoreRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(view.getRootView());
        view.showLoadingIndicator();
        repo.loadScores();
    }

    private void init() {
        user = getIntent().getParcelableExtra(Player.class.getSimpleName());
        repo = new FirebaseScoreRepository();
        repo.setScoresListener(this);
        view = new LeaderBoardViewImp(getLayoutInflater(), null, getSupportFragmentManager());
    }

    @Override
    protected void onDestroy() {
        repo.setScoresListener(null);
        super.onDestroy();
    }

    @Override
    public void onScoresLoad(List<ScoreData> scores) {
        if (scores != null && scores.size() > 0) {
            Collections.sort(scores);
            addScoreFragment(scores);
        }

        view.hideLoadingIndicator();
    }

    private void addScoreFragment(List<ScoreData> scores) {
        ScoresFragment f = new ScoresFragment();
        f.setUser(user);
        f.setScores(scores);
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
