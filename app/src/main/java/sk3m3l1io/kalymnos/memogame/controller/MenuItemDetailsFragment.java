package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.GameMode;
import sk3m3l1io.kalymnos.memogame.view.menu.MenuItemDetailsView;
import sk3m3l1io.kalymnos.memogame.view.menu.MenuItemDetailsViewImp;

public class MenuItemDetailsFragment extends Fragment
        implements MenuItemDetailsView.ButtonClickListener {
    private int titleRes, messageRes;
    private MenuItemDetailsView view;
    private PlayClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PlayClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + PlayClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new MenuItemDetailsViewImp(inflater, container);
        view.setButtonClickListener(this);
        view.setTitle(getString(titleRes));
        view.setMessage(getString(messageRes));
        return view.getRootView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.setButtonClickListener(null);
    }

    @Override
    public void onButtonClick() {
        listener.onPlayClick();
    }

    public interface PlayClickListener {
        void onPlayClick();
    }

    public static MenuItemDetailsFragment getInstanceOf(GameMode mode) {
        MenuItemDetailsFragment f = new MenuItemDetailsFragment();
        f.titleRes = getTitleResOf(mode);
        f.messageRes = getMessageResOf(mode);
        return f;
    }

    private static int getTitleResOf(GameMode m) {
        switch (m) {
            case RANDOM:
                return R.string.random;
            case LIGHTNING_ROUND:
                return R.string.lightning_round;
            default:
                return R.string.practise;
        }
    }

    private static int getMessageResOf(GameMode m) {
        switch (m) {
            case RANDOM:
                return R.string.random_message;
            case LIGHTNING_ROUND:
                return R.string.lightning_round_message;
            default:
                return R.string.practise_message;
        }
    }
}
