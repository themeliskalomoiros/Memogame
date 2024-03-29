package sk3m3l1io.duisburg.memogame.controller.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.view.menu.GameBriefingView;
import sk3m3l1io.duisburg.memogame.view.menu.GameBriefingViewImp;

public class GameBriefingFragment extends Fragment
        implements GameBriefingView.StartGameClickListener {
    private int titleRes, messageRes;
    private GameBriefingView view;
    private OnPlayClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPlayClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + OnPlayClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new GameBriefingViewImp(inflater, container);
        view.setStartGameClickListener(this);
        try {
            // TODO: Fix bug > android.content.res.Resources$NotFoundException: String resource ID #0x0
            // hacky (also lazy) solution
            view.setTitle(getString(titleRes));
            view.setMessage(getString(messageRes));
        } catch (Exception e) {
        }
        return view.getRootView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.setStartGameClickListener(null);
    }

    @Override
    public void onStartGameClick() {
        listener.onPlayClick();
    }

    public interface OnPlayClickListener {
        void onPlayClick();
    }

    public static GameBriefingFragment instanceOf(GameMode mode) {
        GameBriefingFragment f = new GameBriefingFragment();
        f.titleRes = getTitleResOf(mode);
        f.messageRes = getMessageResOf(mode);
        return f;
    }

    private static int getTitleResOf(GameMode m) {
        switch (m) {
            case SURVIVAL:
                return R.string.survival_mode;
            case TIME:
                return R.string.time_mode;
            default:
                return R.string.practise_mode;
        }
    }

    private static int getMessageResOf(GameMode m) {
        switch (m) {
            case SURVIVAL:
                return R.string.survival_mode_message;
            case TIME:
                return R.string.time_mode_message;
            default:
                return R.string.practise_mode_message;
        }
    }
}
