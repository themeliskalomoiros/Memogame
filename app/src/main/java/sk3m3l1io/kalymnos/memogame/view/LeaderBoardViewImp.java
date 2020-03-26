package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sk3m3l1io.kalymnos.memogame.R;

public class LeaderBoardViewImp implements LeaderBoardView {
    private View root;

    public LeaderBoardViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_leader_board, container, false);
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public View getRootView() {
        return root;
    }
}
