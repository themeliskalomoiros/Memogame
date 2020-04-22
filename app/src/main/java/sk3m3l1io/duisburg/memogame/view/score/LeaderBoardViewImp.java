package sk3m3l1io.duisburg.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import sk3m3l1io.duisburg.memogame.R;

public class LeaderBoardViewImp implements LeaderBoardView {
    private View root;
    private TextView title;
    private ProgressBar progressBar;

    public LeaderBoardViewImp(LayoutInflater inflater, ViewGroup container, FragmentManager manager) {
        root = inflater.inflate(R.layout.activity_leaderboard, container, false);
        title = root.findViewById(R.id.title);
        progressBar = root.findViewById(R.id.progressbar);
    }

    @Override
    public View getRootView() {
        return root;
    }

    @Override
    public void setTitle(String t) {
        title.setText(t);
    }

    @Override
    public void setTitle(int res) {
        title.setText(res);
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }
}
