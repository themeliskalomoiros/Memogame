package sk3m3l1io.duisburg.memogame.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sk3m3l1io.duisburg.memogame.view.menu.MenuView;
import sk3m3l1io.duisburg.memogame.view.menu.MenuViewImp;

public class MenuFragment extends Fragment implements MenuView.SymbolClickListener {

    private MenuView view;
    private MenuItemClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MenuItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + MenuItemClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = new MenuViewImp(inflater, container);
        view.setMenuClickListener(this);
        return view.getRootView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.setMenuClickListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setDefaultSignInIcon() {
        view.setUserIconToDefault();
    }

    public void setSignOutIcon() {
        view.setUserIconToSignOut();
    }

    @Override
    public void onLightningClick() {
        listener.onArcadeModeClick();
    }

    @Override
    public void onDiceClick() {
        listener.onRandomModeClick();
    }

    @Override
    public void onJoystickClick() {
        listener.onPractiseModeClick();
    }

    @Override
    public void onLeaderboardClick() {
        listener.onLeaderboardClick();
    }

    @Override
    public void onUserClick() {
        listener.onSignInClick();
    }

    public interface MenuItemClickListener {
        void onArcadeModeClick();

        void onRandomModeClick();

        void onPractiseModeClick();

        void onSignInClick();

        void onLeaderboardClick();
    }
}
