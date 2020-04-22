package sk3m3l1io.duisburg.memogame.controller.stats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
import sk3m3l1io.duisburg.memogame.services.ScoreDetailMessage;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;
import sk3m3l1io.duisburg.memogame.view.score.ScoreDetailsView;
import sk3m3l1io.duisburg.memogame.view.score.ScoreDetailsViewImp;

public class ScoreDetailsFragment extends Fragment {
    private ScoreData sd;
    private int rank;
    private ScoreDetailsView view;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new ScoreDetailsViewImp(inflater, container);
        return view.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view.setUserName(sd.getPlayer().getName());
        ScoreDetailMessage msg = new ScoreDetailMessage(sd, rank);
        view.setMessage(msg.getLocalizedMessage());
        view.setSurvivalPoints(sd.getSurvivalHighScore());
        view.setTimePoints(sd.getTimeHighScore());
        view.setAveragePoints((int) sd.getGameAverageGamePoints());
        view.setCompletedGamesPoints((int) sd.getGamesCompletedPoints());
        Log.d(LogUtils.TAG, "Accuracy points: "+sd.getAccuracyPoints());
        view.setAccuracyPoints((int) sd.getAccuracyPoints());
        view.setBadgesPoints((int) sd.getBadgePoints());
        view.setTotalPoints(sd.getTotalPoints());

        String imageUrl = sd.getPlayer().getPhotoUrl();
        if (imageUrl != null) {
            view.setUserImage(imageUrl);
            view.showUserImage();
        }

        if (hasWonMedal())
            updateMedalUi();

        if (hasWonBadges())
            updateBadgesUi();
    }

    private boolean hasWonMedal() {
        return rank == 0 || rank == 1 || rank == 2;
    }

    private void updateMedalUi() {
        if (rank == 0) {
            view.setMedal(R.drawable.medal_first_place);
        } else if (rank == 1) {
            view.setMedal(R.drawable.medal_second_place);
        } else {
            view.setMedal(R.drawable.medal_third_place);
        }

        view.showMedal();
    }

    private boolean hasWonBadges() {
        return sd.isTimeCompleted() || sd.isSurvivalCompleted();
    }

    private void updateBadgesUi() {
        if (sd.isSurvivalCompleted())
            view.showRightBadge();

        if (sd.isTimeCompleted())
            view.showLeftBadge();
    }

    public void setScoreData(ScoreData sd) {
        this.sd = sd;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
