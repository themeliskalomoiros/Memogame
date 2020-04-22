package sk3m3l1io.duisburg.memogame.controller.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;
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
        view.setMessage(getMessage());
        view.setSurvivalPoints(sd.getSurvivalHighScore());
        view.setTimePoints(sd.getTimeHighScore());
        view.setAveragePoints((int) sd.getGameAverageGamePoints());
        view.setCompletedGamesPoints((int) sd.getGamesCompletedPoints());
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

    private String getMessage() {
        String language = Locale.getDefault().getISO3Language();
        switch (language) {
            case "ell":
                return getGreekMessage();
            // TODO: implement a german message
            default:
                return getDefaultMessage();
        }
    }

    private String getDefaultMessage() {
        String firstName = sd.getPlayer().getName().split(" ")[0];
        double accuracy = sd.getMatches() / (sd.getMatches() + sd.getFailedMatches()) * 100;
        String s1 = String.format("%s completed successfully %d games with %.1f%% accuracy.", firstName, sd.getGamesCompleted(), accuracy);
        int badges = 0;
        if (sd.isTimeCompleted()) badges++;
        if (sd.isSurvivalCompleted()) badges++;
        String s2 = String.format("He/She gained %d badges because of excellence shown in %s and %s modes.", badges, getContext().getString(R.string.time_mode), getContext().getString(R.string.survival_mode));
        String s3 = String.format("Currently ranks %d.", rank);
        return s1 + " " + s2 + " " + s3;
    }

    private String getGreekMessage() {
        String firstName = sd.getPlayer().getName().split(" ")[0];
        double accuracy = (sd.getMatches() * 1.0) / (sd.getMatches() + sd.getFailedMatches()) * 100;
        String s1 = String.format("O/H %s ολοκλήρωσε με επιτυχία %d παιχνίδια με ακρίβεια %.1f%%.", firstName, sd.getGamesCompleted(), accuracy);
        int badges = 0;
        if (sd.isTimeCompleted()) badges++;
        if (sd.isSurvivalCompleted()) badges++;
        String s2 = String.format("Κέρδισε %d παράσημα λόγω τέλειας επίδοσης στους γύρους %s/%s.", badges, getContext().getString(R.string.time_mode), getContext().getString(R.string.survival_mode));
        String s3 = String.format("Αυτή τη στιγμή βρίσκεται στην θέση %dη θέση.", rank);
        return s1 + " " + s2 + " " + s3;
    }

    public void setScoreData(ScoreData sd) {
        this.sd = sd;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
