package sk3m3l1io.duisburg.memogame.view.score;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;

public class ScoreViewImp implements ScoreView {
    private View root;
    private RecyclerView recyclerView;
    private ScoreAdapter adapter;

    public ScoreViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_scores, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new ScoreAdapter(root.getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setOnScoreDetailsClickListener(OnScoreDetailsClickListener listener) {
        adapter.setOnScoreDetailsClickListener(listener);
    }

    @Override
    public void setScores(List<ScoreData> scores, Player user) {
        adapter.setScores(scores);
        adapter.setUser(user);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View getRootView() {
        return root;
    }
}
