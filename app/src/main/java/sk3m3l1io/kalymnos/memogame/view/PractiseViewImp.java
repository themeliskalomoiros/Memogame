package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sk3m3l1io.kalymnos.memogame.R;

public class PractiseViewImp implements PractiseView {
    private final View root;
    private final TextView title;
    private final ProgressBar progressBar;
    private final FloatingActionButton next, previous;
    private ChangeGameClickListener changeGameClickListener;

    public PractiseViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_practise, container, false);
        progressBar = root.findViewById(R.id.progressbar);
        title = root.findViewById(R.id.title);
        next = root.findViewById(R.id.next);
        next.setOnClickListener(b -> changeGameClickListener.onNextGameClick());
        previous = root.findViewById(R.id.previous);
        previous.setOnClickListener(b -> changeGameClickListener.onPreviousGameClick());
    }

    @Override
    public void setChangeGameClickListener(ChangeGameClickListener listener) {
        changeGameClickListener = listener;
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
