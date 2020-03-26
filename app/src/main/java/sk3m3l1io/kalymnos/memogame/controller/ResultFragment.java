package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.os.Bundle;
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
    private RestartClickListener restartClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            restartClickListener = (RestartClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + RestartClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = setupView(inflater, container);
        return root;
    }

    private View setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_result, container, false);
        TextView r = root.findViewById(R.id.result);
        r.setText(getString(R.string.you_did) + " " + getString(getResult().getNameRes()));
        TextView m = root.findViewById(R.id.message);
        m.setText(createMsg());
        TextView c = root.findViewById(R.id.games_completed);
        c.setText("" + gamesCompleted);
        FloatingActionButton b = root.findViewById(R.id.exit);
        b.setOnClickListener(v -> restartClickListener.onRestartClick());
        return root;
    }

    private String createMsg() {
        String prefix = getString(R.string.lightning_round_completed_prefix) + " " +
                gamesCompleted + " " +
                getString(R.string.out_of_sufix) + " " +
                gameCount + ".";
        String suffix = getString(getResult().getMessageRes());
        return prefix + suffix;
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

    public void setRestartClickListener(RestartClickListener listener) {
        restartClickListener = listener;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public interface RestartClickListener {
        void onRestartClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setRestartClickListener(null);
    }

    private enum Result {
        PERFECT,
        VERY_GOOD,
        GOOD,
        BAD;

        int getNameRes(){
            switch (this){
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

        int getMessageRes(){
            switch (this){
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
