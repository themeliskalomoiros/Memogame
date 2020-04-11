package sk3m3l1io.duisburg.memogame.controller.game;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.services.Score;
import sk3m3l1io.duisburg.memogame.view.score.ResultView;
import sk3m3l1io.duisburg.memogame.view.score.ResultViewImp;

public class ResultFragment extends Fragment implements ResultView.ButtonClickListener {
    private int gameCount;
    private List<Game> completedGames;
    private ResultView view;
    private ResultButtonClickListener resultButtonClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            resultButtonClickListener = (ResultButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + ResultButtonClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = new ResultViewImp(inflater, container);
        view.setButtonClickListener(this);
        return view.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (completedGames != null) // bug fix (hack)
            updateUI();
    }

    private void updateUI() {
        view.setTitle(getTitle());
        view.setMessage(getMessage());
        int score = Score.calculate(completedGames);
        view.setScore("" + score + " " + getString(R.string.points));
        if (score > 0) {
            view.setGameDetails(getDetails());
        } else {
            view.hideDetails();
        }
    }

    private String getDetails() {
        int easy = 0;
        int normal = 0;
        int hard = 0;
        for (Game g : completedGames) {
            if (g.getDifficulty() == GameDifficulty.EASY) {
                ++easy;
            } else if (g.getDifficulty() == GameDifficulty.HARD) {
                ++hard;
            } else {
                ++normal;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.score_analysis) + "\n");
        if (easy > 0) {
            sb.append(getDetailLine(easy, getString(R.string.difficulity_easy), Score.POINTS_EASY) + "\n");
        }
        if (normal > 0) {
            sb.append(getDetailLine(normal, getString(R.string.difficulity_normal), Score.POINTS_NORMAL) + "\n");
        }
        if (hard > 0) {
            sb.append(getDetailLine(hard, getString(R.string.difficulity_hard), Score.POINTS_HARD));
        }
        return sb.toString();
    }

    private String getDetailLine(int count, String difficulty, int points) {
        return String.format("%d %s (x %d)", count, difficulty, points);
    }

    private String getTitle() {
        String prefix = getString(R.string.you_did) + " ";
        String result = getString(getResult().getNameRes()) + " ";
        return prefix + result;
    }

    private String getMessage() {
        String prefix = getString(R.string.arcade_completed_prefix) + " " +
                completedGames.size() + " " +
                getString(R.string.out_of_sufix) + " " +
                gameCount + ". ";
        String suffix = getString(getResult().getMessageRes());
        return prefix + suffix;
    }

    private ResultFragment.Result getResult() {
        double percent = completedGames.size() / (gameCount * 1.0);
        if (percent >= 0d && percent < 0.3d) {
            return ResultFragment.Result.BAD;
        } else if (percent >= 0.3d && percent < 0.7d) {
            return ResultFragment.Result.GOOD;
        } else if (percent >= 0.7d && percent < 0.99d) {
            return Result.VERY_GOOD;
        } else {
            return Result.PERFECT;
        }
    }

    public void setCompletedGames(List<Game> completedGames) {
        this.completedGames = completedGames;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    @Override
    public void onButtonClick() {
        resultButtonClickListener.onResultButtonClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.setButtonClickListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resultButtonClickListener = null;
    }

    public interface ResultButtonClickListener {
        void onResultButtonClick();
    }

    private enum Result {
        PERFECT,
        VERY_GOOD,
        GOOD,
        BAD;

        int getNameRes() {
            switch (this) {
                case PERFECT:
                    return R.string.result_perfect;
                case VERY_GOOD:
                    return R.string.result_very_good;
                case GOOD:
                    return R.string.result_good;
                default:
                    return R.string.result_bad;
            }
        }

        int getMessageRes() {
            switch (this) {
                case PERFECT:
                    return R.string.result_perfect_message;
                case VERY_GOOD:
                    return R.string.result_very_good_message;
                case GOOD:
                    return R.string.result_good_message;
                default:
                    return R.string.result_bad_message;
            }
        }
    }
}
