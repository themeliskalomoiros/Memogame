package sk3m3l1io.duisburg.memogame.controller.menu;

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
    private MenuItemClickListener itemClickListener;
    private OnViewCreationListener viewCreationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            itemClickListener = (MenuItemClickListener) context;
            viewCreationListener = (OnViewCreationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement " + MenuItemClickListener.class.getSimpleName() +
                    " and " + OnViewCreationListener.class.getSimpleName());
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewCreationListener.onMenuFragmentViewCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view.setMenuClickListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemClickListener = null;
        viewCreationListener = null;
    }

    public void setSignInUI() {
        view.setSignIn();
    }

    public void setSignOutUI() {
        view.setSignOut();
    }

    @Override
    public void onTimeClick() {
        itemClickListener.onTimeModeClick();
    }

    @Override
    public void onSurvivalClick() {
        itemClickListener.onSurvivalModeClick();
    }

    @Override
    public void onPractiseClick() {
        itemClickListener.onPractiseModeClick();
    }

    @Override
    public void onLeaderboardClick() {
        itemClickListener.onLeaderboardClick();
    }

    @Override
    public void onSignInClick() {
        itemClickListener.onSignInClick();
    }

    public interface MenuItemClickListener {
        void onTimeModeClick();

        void onSurvivalModeClick();

        void onPractiseModeClick();

        void onSignInClick();

        void onLeaderboardClick();
    }

    public interface OnViewCreationListener {
        void onMenuFragmentViewCreated();
    }
}
