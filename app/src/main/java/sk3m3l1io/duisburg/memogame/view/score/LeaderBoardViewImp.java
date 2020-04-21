package sk3m3l1io.duisburg.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import sk3m3l1io.duisburg.memogame.R;

public class LeaderBoardViewImp implements LeaderBoardView {
    private View root;
    private TextView title;

    public LeaderBoardViewImp(LayoutInflater inflater, ViewGroup container, FragmentManager manager) {
        root = inflater.inflate(R.layout.activity_leaderboard, container, false);
        title = root.findViewById(R.id.title);
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
}
