package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sk3m3l1io.kalymnos.memogame.R;

public class ResultFragment extends Fragment {
    private int gameCount;
    private int gamesCompleted;
    private ExitResultClickListener exitResultClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            exitResultClickListener = (ExitResultClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + ExitResultClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lightning_result, container, false);
        TextView m = root.findViewById(R.id.message);
        m.setText(createMsg());
        TextView c = root.findViewById(R.id.games_completed);
        c.setText("" + gamesCompleted);
        FloatingActionButton b = root.findViewById(R.id.exit);
        b.setOnClickListener(v -> exitResultClickListener.onExitResultClick());
        return root;
    }

    private String createMsg() {
        String prefix = getString(R.string.lightning_round_completed_prefix) + " " +
                gamesCompleted +
                getString(R.string.out_of_sufix) +
                gameCount + ".";
        String suffix = getString(R.string.result_prefix) + getResultText();
        return prefix + suffix;
    }

    private String getResultText() {
        Result result = getResult();
        String suffix;
        if (result == Result.PERFECT) {
            suffix = getString(R.string.perfect);
        } else if (result == Result.VERY_GOOD) {
            suffix = getString(R.string.very_good);
        } else if (result == Result.GOOD) {
            suffix = getString(R.string.good);
        } else {
            suffix = getString(R.string.bad);
        }
        return suffix;
    }

    private ResultFragment.Result getResult() {
        double percent = gamesCompleted / (gameCount * 1.0);
        if (percent >= 0d && percent < 0.3d) {
            return ResultFragment.Result.BAD;
        } else if (percent >= 0.3d && percent < 0.7d) {
            return ResultFragment.Result.GOOD;
        } else if (percent >= 0.7d && percent < 0.99d){
            return Result.VERY_GOOD;
        }else{
            return Result.PERFECT;
        }
    }

    public void setGamesCompleted(int gamesCompleted) {
        this.gamesCompleted = gamesCompleted;
    }

    public void setExitResultClickListener(ExitResultClickListener listener) {
        exitResultClickListener = listener;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public interface ExitResultClickListener {
        void onExitResultClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setExitResultClickListener(null);
    }

    private enum Result {
        PERFECT,
        VERY_GOOD,
        GOOD,
        BAD
    }
}
