package sk3m3l1io.duisburg.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import sk3m3l1io.duisburg.memogame.R;

public class MainViewImp implements MainView {
    private View root;
    private ProgressBar loadingBar;

    public MainViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_main, container, false);
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
    public int getMenuContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public View getRootView() {
        return root;
    }
}
