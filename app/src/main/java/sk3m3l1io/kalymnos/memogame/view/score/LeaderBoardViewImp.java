package sk3m3l1io.kalymnos.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import sk3m3l1io.kalymnos.memogame.R;

public class LeaderBoardViewImp implements LeaderBoardView {
    private View root;
    private ProgressBar progressBar;

    public LeaderBoardViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_leader_board, container, false);
        progressBar = root.findViewById(R.id.progressbar);
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public View getRootView() {
        return root;
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
