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

public class ScoresFragment extends Fragment implements
        ScoreRepository.ScoreDataListener,
        ScoreView.OnScoreDetailsClickListener {
    private Player user;
    private ScoreView view;
    protected ScoreRepository repo;
    private List<ScoreData> scores;
    OnScoreDetailsClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnScoreDetailsClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + OnScoreDetailsClickListener.class.getSimpleName());
        }
    }

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
        view.setOnScoreDetailsClickListener(this);
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
            this.scores = scores;
        }
    }

    public void setUser(Player user) {
        this.user = user;
    }

    public void setOnScoreDetailsClickListener(OnScoreDetailsClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onScoreDetailsClicked(int position) {
        listener.onScoreDetailsClick(scores.get(position), position);
    }

    interface OnScoreDetailsClickListener {
        void onScoreDetailsClick(ScoreData sd, int rank);
    }
}
