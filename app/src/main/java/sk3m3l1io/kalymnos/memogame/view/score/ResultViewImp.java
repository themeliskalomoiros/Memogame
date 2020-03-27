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

    public ResultViewImp(LayoutInflater inflater, ViewGroup container){
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
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void setGameDetails(String details) {
        this.details.setText(details);
    }

    @Override
    public void setScore(String score) {
        this.score.setText(score);
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
