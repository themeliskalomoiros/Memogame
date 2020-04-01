package sk3m3l1io.duisburg.memogame.view.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;

public class PractiseViewImp implements PractiseView {
    private final View root;
    private final TextView title, difficulty;
    private final FloatingActionButton next, previous;
    private ChangeGameClickListener changeGameClickListener;

    public PractiseViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_practise, container, false);
        title = root.findViewById(R.id.title);
        difficulty = root.findViewById(R.id.difficulty);
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
    public void setDifficulty(GameDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                this.difficulty.setText(R.string.difficulity_easy);
                this.difficulty.setTextColor(root.getResources().getColor(R.color.symbolMatchLightColor));
                break;
            case HARD:
                this.difficulty.setText(R.string.difficulity_hard);
                this.difficulty.setTextColor(root.getResources().getColor(R.color.secondaryColor));
                break;
            default:
                this.difficulty.setText(R.string.difficulity_normal);
                this.difficulty.setTextColor(root.getResources().getColor(R.color.symbolMatchColor));
        }
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
    public int getGameContainerId() {
        return R.id.game_container;
    }

    @Override
    public View getRootView() {
        return root;
    }
}
