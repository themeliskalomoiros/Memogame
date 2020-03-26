package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;

public class LightningViewImp implements LightningView {
    private final View root;
    private final TextView title, time;
    private final ProgressBar progressBar;

    public LightningViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_lightning, container, false);
        progressBar = root.findViewById(R.id.progressbar);
        title = root.findViewById(R.id.title);
        time = root.findViewById(R.id.time);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTimeProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void setTimeMaxProgress(int progress) {
        progressBar.setMax(progress);
    }

    @Override
    public int getGameContainerId() {
        return R.id.game_container;
    }

    @Override
    public View getRootView() {
        return root;
    }
}
