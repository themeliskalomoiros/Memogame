package sk3m3l1io.duisburg.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sk3m3l1io.duisburg.memogame.R;

public class GameBriefingViewImp implements GameBriefingView {
    private View root;
    private FloatingActionButton play;
    private TextView title, message;
    private StartGameClickListener listener;

    public GameBriefingViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_game_briefing, container, false);
        play = root.findViewById(R.id.fab);
        play.setOnClickListener(v -> listener.onStartGameClick());
        title = root.findViewById(R.id.title);
        message = root.findViewById(R.id.message);
    }

    @Override
    public void setStartGameClickListener(StartGameClickListener listener) {
        this.listener = listener;
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
    public void setMessage(String m) {
        this.message.setText(m);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
