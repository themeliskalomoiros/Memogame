package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.model.FakePlayerRepository;
import sk3m3l1io.kalymnos.memogame.model.PlayerRepository;
import sk3m3l1io.kalymnos.memogame.pojos.Player;
import sk3m3l1io.kalymnos.memogame.view.score.ScoreView;
import sk3m3l1io.kalymnos.memogame.view.score.ScoreViewImp;

public class ScoresFragment extends Fragment
        implements PlayerRepository.PlayerDataListener {
    private ScoreView view;
    private PlayerRepository repo;
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
        repo = new FakePlayerRepository();
        repo.setPlayerDataListener(this);
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
        repo.loadPlayers();
        listener.onLoadBegun();
    }

    @Override
    public void onPlayersLoaded(List<Player> players) {
        if (players != null && players.size() > 0) {
            view.setPlayers(players);
            listener.onLoadFinished();
        }
    }

    interface ScoresLoadListener {
        void onLoadBegun();

        void onLoadFinished();
    }
}
