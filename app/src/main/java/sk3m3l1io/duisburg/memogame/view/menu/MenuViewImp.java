package sk3m3l1io.duisburg.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import sk3m3l1io.duisburg.memogame.R;

public class MenuViewImp implements MenuView {
    private View root;
    private MaterialButton time, survival, leaderboard, signIn, practise;
    private SymbolClickListener listener;

    public MenuViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        time = root.findViewById(R.id.lightning_round);
        time.setOnClickListener(v -> listener.onTimeClick());
        practise = root.findViewById(R.id.practise);
        practise.setOnClickListener(v -> listener.onPractiseClick());
        survival = root.findViewById(R.id.random);
        survival.setOnClickListener(v -> listener.onSurvivalClick());
        leaderboard = root.findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(v -> listener.onLeaderboardClick());
        signIn = root.findViewById(R.id.user);
        signIn.setOnClickListener(v -> listener.onSignInClick());
    }

    @Override
    public void setMenuClickListener(SymbolClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setSignIn() {
        signIn.setText(R.string.sign_in);
    }

    @Override
    public void setSignOut() {
        signIn.setText(R.string.sign_out);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
