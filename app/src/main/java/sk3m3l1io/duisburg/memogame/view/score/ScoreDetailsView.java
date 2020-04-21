package sk3m3l1io.duisburg.memogame.view.score;

import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface ScoreDetailsView extends ViewMvc {
    void setUserImage(String url);

    void showUserImage();

    void setMedal(int res);

    void showMedal();

    void showLeftBadge();

    void showRightBadge();

    void setUserName(String name);

    void setMessage(String msg);

    void setSurvivalPoints(int points);

    void setTimePoints(int points);

    void setAveragePoints(int points);

    void setCompletedGamesPoints(int points);

    void setAccuracyPoints(int points);

    void setBadgesPoints(int points);

    void setTotalPoints(int points);
}
