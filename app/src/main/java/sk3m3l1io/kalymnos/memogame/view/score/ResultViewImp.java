package sk3m3l1io.kalymnos.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sk3m3l1io.kalymnos.memogame.R;

public class ResultViewImp implements ResultView {
    private View root;
    private FloatingActionButton button;
    private TextView title, message, score, details;

    public ResultViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_result, container, false);
        button = root.findViewById(R.id.fab);
        title = root.findViewById(R.id.title);
        message = root.findViewById(R.id.message);
        score = root.findViewById(R.id.score);
        details = root.findViewById(R.id.details);
    }

    @Override
    public void setButtonClickListener(ButtonClickListener listener) {
        button.setOnClickListener(v -> listener.onButtonClick());
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
    public void setGameDetails(String d) {
        this.details.setText(d);
    }

    @Override
    public void setScore(String s) {
        this.score.setText(s);
    }

    @Override
    public void hideDetails() {
        details.setVisibility(View.GONE);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
