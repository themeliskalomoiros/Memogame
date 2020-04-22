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

public class ScoresFragment extends Fragment implements ScoreView.OnScoreDetailsClickListener {
    private Player user;
    private ScoreView view;
    OnScoreDetailsClickListener listener;
    private List<ScoreData> scores;

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
        view.setScores(scores, user);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public void setUser(Player user) {
        this.user = user;
    }

    public void setOnScoreDetailsClickListener(OnScoreDetailsClickListener listener){
        this.listener = listener;
    }

    public void setScores(List<ScoreData> scores) {
        this.scores = scores;
    }

    @Override
    public void onScoreDetailsClicked(int position) {
        listener.onScoreDetailsClick(scores.get(position), position);
    }

    interface OnScoreDetailsClickListener {
        void onScoreDetailsClick(ScoreData sd, int rank);
    }
}
