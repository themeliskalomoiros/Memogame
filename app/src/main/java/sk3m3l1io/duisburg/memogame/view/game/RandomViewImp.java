package sk3m3l1io.duisburg.memogame.view.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;

public class RandomViewImp implements RandomView {
    private final View root;
    private final TextView title, difficulty, lives;

    public RandomViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_random, container, false);
        title = root.findViewById(R.id.title);
        lives = root.findViewById(R.id.lives);
        difficulty = root.findViewById(R.id.difficulty);
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
    public View getRootView() {
        return root;
    }

    @Override
    public void setLives(int l) {
        this.lives.setText("" + l);
    }
}
