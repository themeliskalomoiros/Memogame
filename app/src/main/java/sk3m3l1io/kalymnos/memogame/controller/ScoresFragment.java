package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import sk3m3l1io.kalymnos.memogame.model.FakeScoreRepository;
import sk3m3l1io.kalymnos.memogame.model.ScoreRepository;
import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.view.score.ScoreView;
import sk3m3l1io.kalymnos.memogame.view.score.ScoreViewImp;

public class ScoresFragment extends Fragment implements ScoreRepository.ScoreListener {
    private ScoreView view;
    private ScoreRepository repo;
    private ScoresLoadListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ScoresLoadListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + ScoresLoadListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Replace with real repository
        repo = new FakeScoreRepository();
        repo.setScoreListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = new ScoreViewImp(inflater, container);
        return view.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repo.loadScores();
        listener.onLoadBegun();
    }

    @Override
    public void onScoresLoaded(Map<Integer, Player> scores) {
        if (scores != null && scores.size() > 0) {
            SortedMap<Integer, Player> sortedScores = new TreeMap<>((s1, s2) -> s2 - s1);
            sortedScores.putAll(scores);
            int[] s = getKeysFrom(sortedScores.keySet());
            Player[] p = getPlayersFrom(sortedScores.values());
            view.setScores(s, p);
        }
    }

    private int[] getKeysFrom(Set<Integer> keys) {
        int[] s = new int[keys.size()];
        Iterator<Integer> it = keys.iterator();
        int i = 0;
        while (it.hasNext()){
            s[i++] = it.next();
        }
        return s;
    }

    private Player[] getPlayersFrom(Collection<Player> values) {
        Player[] p = new Player[values.size()];
        Iterator<Player> it = values.iterator();
        int i = 0;
        while (it.hasNext()){
            p[i++] = it.next();
        }
        return p;
    }

    interface ScoresLoadListener {
        void onLoadBegun();

        void onLoadFinished();
    }
}
