package sk3m3l1io.kalymnos.memogame.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.kalymnos.memogame.R;

public class LightningResultFragment extends Fragment {

    private Result result;
    private int gamesCompleted;

    public void setResult(Result result) {
        this.result = result;
    }

    public void setGamesCompleted(int gamesCompleted) {
        this.gamesCompleted = gamesCompleted;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lightning_result, container, false);
        TextView message = root.findViewById(R.id.message);
        message.setText(createMsg());
        return root;
    }

    private String createMsg() {
        String prefix = getString(R.string.lightning_round_completed) + " " +
                gamesCompleted + " " +
                getString(R.string.games);

        String suffix = "";
        if (result == Result.PERFECT) {
            suffix = getString(R.string.excellent);
        } else if (result == Result.VERY_GOOD) {
            suffix = getString(R.string.very_good);
        } else if (result == Result.GOOD) {
            suffix = getString(R.string.good);
        } else {
            suffix = getString(R.string.bad);
        }

        return prefix + suffix;
    }

    public enum Result {
        PERFECT,
        VERY_GOOD,
        GOOD,
        BAD
    }
}
