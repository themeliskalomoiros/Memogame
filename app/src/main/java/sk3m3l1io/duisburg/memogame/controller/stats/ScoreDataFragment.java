package sk3m3l1io.duisburg.memogame.controller.stats;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.model.repos.FirebaseScoreRepository;
import sk3m3l1io.duisburg.memogame.model.repos.ScoreRepository;
import sk3m3l1io.duisburg.memogame.view.score.ScoreView;
import sk3m3l1io.duisburg.memogame.view.score.ScoreViewImp;

public class ScoreDataFragment extends Fragment implements ScoreRepository.ScoreDataListener {
    private Player user;
    private ScoreView view;
    protected ScoreRepository repo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new FirebaseScoreRepository();
        repo.setScoresListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new ScoreViewImp(inflater, container);
        return view.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repo.loadScores();
        view.showLoadingIndicator();
    }

    @Override
    public void onDestroy() {
        repo.setScoresListener(null);
        super.onDestroy();
    }

    @Override
    public void onScoreDataLoad(List<ScoreData> scores) {
        if (scores != null && scores.size() > 0) {
            Collections.sort(scores);
            view.setScores(scores, user);
            view.hideLoadingIndicator();
        }
    }

    public void setUser(Player user) {
        this.user = user;
    }
}
