package sk3m3l1io.duisburg.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.PlayerScore;

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
    public void setScores(List<PlayerScore> scores, Player user) {
        adapter.setScores(scores);
        adapter.setUser(user);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View getRootView() {
        return root;
    }
}
