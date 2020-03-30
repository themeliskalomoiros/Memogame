package sk3m3l1io.kalymnos.memogame.view.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.GameDifficulty;

public class RandomViewImp implements RandomView {
    private final View root;
    private final TextView title, difficulty;
    private final ProgressBar timeBar, healthBar;

    public RandomViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_random, container, false);
        timeBar = root.findViewById(R.id.progressbar);
        healthBar = root.findViewById(R.id.health_bar);
        title = root.findViewById(R.id.title);
        difficulty = root.findViewById(R.id.difficulty);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTimeProgress(int progress) {
        timeBar.setProgress(progress);
    }

    @Override
    public void setTimeProgressMax(int progress) {
        timeBar.setMax(progress);
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
    public void setHealthBarMax(int max) {
        healthBar.setMax(max);
    }

    @Override
    public void setHealthbarValue(int value) {
        healthBar.setProgress(value);
    }
}
