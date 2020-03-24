package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sk3m3l1io.kalymnos.memogame.R;

public class MainScreenImp implements MainScreen {
    private View root;
    private Button play;
    private PlayClickListener playClickListener;

    public MainScreenImp(LayoutInflater inflater, ViewGroup container) {
        initViews(inflater, container);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_main, container, false);
        play = root.findViewById(R.id.play);
        play.setOnClickListener(view -> {
            if (playClickListener != null)
                playClickListener.onPlayClick();
        });
    }

    @Override
    public void setPlayClickListener(PlayClickListener listener) {
        playClickListener = listener;
    }

    @Override
    public View getRootView() {
        return root;
    }
}
