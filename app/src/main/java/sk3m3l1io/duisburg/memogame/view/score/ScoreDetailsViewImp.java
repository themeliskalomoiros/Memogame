package sk3m3l1io.duisburg.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import sk3m3l1io.duisburg.memogame.R;

public class ScoreDetailsViewImp implements ScoreDetailsView {
    private View root;
    private ImageView image, medal, leftBadge, rightBadge;
    private TextView name, message, survivalPoints, timePoints,
            averagePoints, gamesCompletedPoints, accuracyPoints, badgesPoints, totalPoints;
    private ViewGroup badgeContainer;

    public ScoreDetailsViewImp(LayoutInflater inflater, ViewGroup parent) {
        root = inflater.inflate(R.layout.fragment_score_details, parent, false);
        image = root.findViewById(R.id.image);
        medal = root.findViewById(R.id.medal);
        leftBadge = root.findViewById(R.id.badge_left);
        rightBadge = root.findViewById(R.id.badge_right);
        name = root.findViewById(R.id.name);
        message = root.findViewById(R.id.message);
        survivalPoints = root.findViewById(R.id.survival_highscore);
        timePoints = root.findViewById(R.id.time_highscore);
        averagePoints = root.findViewById(R.id.average_highscore);
        gamesCompletedPoints = root.findViewById(R.id.games_completed);
        accuracyPoints = root.findViewById(R.id.accuracy);
        badgesPoints = root.findViewById(R.id.badges);
        totalPoints = root.findViewById(R.id.total_points);
        badgeContainer = root.findViewById(R.id.badges_container);
    }

    @Override
    public void setUserImage(String url) {
        Glide.with(root.getContext()).load(url).placeholder(R.drawable.user_male).into(image);
    }

    @Override
    public void showUserImage() {
        image.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMedal(int res) {
        medal.setImageResource(res);
    }

    @Override
    public void showMedal() {
        badgeContainer.setVisibility(View.VISIBLE);
        medal.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLeftBadge() {
        badgeContainer.setVisibility(View.VISIBLE);
        leftBadge.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRightBadge() {
        badgeContainer.setVisibility(View.VISIBLE);
        rightBadge.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setMessage(String msg) {
        message.setText(msg);
    }

    @Override
    public void setSurvivalPoints(int points) {
        survivalPoints.setText("" + points);
    }

    @Override
    public void setTimePoints(int points) {
        timePoints.setText("" + points);
    }

    @Override
    public void setAveragePoints(int points) {
        averagePoints.setText("" + points);
    }

    @Override
    public void setCompletedGamesPoints(int points) {
        gamesCompletedPoints.setText("" + points);
    }

    @Override
    public void setAccuracyPoints(int points) {
        accuracyPoints.setText("" + points);
    }

    @Override
    public void setBadgesPoints(int points) {
        badgesPoints.setText("" + points);
    }

    @Override
    public void setTotalPoints(int points) {
        totalPoints.setText("" + points);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
