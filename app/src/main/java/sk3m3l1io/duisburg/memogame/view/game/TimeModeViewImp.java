package sk3m3l1io.duisburg.memogame.view.game;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.GameDifficulty;

public class TimeModeViewImp implements TimeModeView {
    private final View root;
    private final TextView title, gamesCompleted, difficulty;
    private final ProgressBar progressBar;

    public TimeModeViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_time, container, false);
        progressBar = root.findViewById(R.id.progressbar);
        title = root.findViewById(R.id.title);
        difficulty = root.findViewById(R.id.difficulty);
        gamesCompleted = root.findViewById(R.id.games_completed);
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
    public void setTimeProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void setTimeProgressMax(int progress) {
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

    @Override
    public void setCompletedGamesCount(int c) {
        gamesCompleted.setText("" + c);
    }

    @Override
    public int getTimerProgress() {
        return progressBar.getProgress();
    }

    @Override
    public int getTimerMaxProgress() {
        return progressBar.getMax();
    }

    @Override
    public void showTimer() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTimer() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isTimerVisible() {
        return progressBar.getVisibility() == View.VISIBLE ? true : false;
    }

    @Override
    public void setDifficulty(GameDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                this.difficulty.setText(R.string.difficulity_easy);
                this.difficulty.setTextColor(root.getResources().getColor(R.color.secondaryColor100));
                break;
            case HARD:
                this.difficulty.setText(R.string.difficulity_hard);
                this.difficulty.setTextColor(root.getResources().getColor(R.color.secondaryColor));
                break;
            default:
                this.difficulty.setText(R.string.difficulity_normal);
                this.difficulty.setTextColor(root.getResources().getColor(R.color.secondaryDarkColor));
        }
    }
}
