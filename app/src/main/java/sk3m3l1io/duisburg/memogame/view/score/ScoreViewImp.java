package sk3m3l1io.duisburg.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.pojos.Player;

public class ScoreViewImp implements ScoreView {
    private View root;
    private RecyclerView recyclerView;
    private ScoreAdapter adapter;

    public ScoreViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_scores, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new ScoreAdapter(root.getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setScores(int[] scores, Player[] players, Player user) {
        adapter.setScores(scores);
        adapter.setPlayers(players);
        adapter.setUser(user);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View getRootView() {
        return root;
    }
}
