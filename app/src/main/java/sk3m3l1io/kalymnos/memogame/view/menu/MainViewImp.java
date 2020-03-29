package sk3m3l1io.kalymnos.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;

public class MainViewImp implements MainView {
    private View root;
    private TextView name;
    private ProgressBar loadingBar;

    public MainViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_main, container, false);
        name = root.findViewById(R.id.player_name);
        loadingBar = root.findViewById(R.id.progressbar);
    }

    @Override
    public void showLoadingIndicator() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void setPlayerName(String name) {
        this.name.setText(name);
    }

    @Override
    public int getMenuContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void showPlayerName() {
        name.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlayerName() {
        name.setVisibility(View.GONE);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
