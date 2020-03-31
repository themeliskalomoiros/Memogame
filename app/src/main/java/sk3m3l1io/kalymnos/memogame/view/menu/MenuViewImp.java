package sk3m3l1io.kalymnos.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import sk3m3l1io.kalymnos.memogame.R;

public class MenuViewImp implements MenuView {
    private View root;
    private ImageButton lightning, practise, random, leaderboard, user;
    private SymbolClickListener listener;

    public MenuViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        lightning = root.findViewById(R.id.lightning_round);
        lightning.setOnClickListener(v -> listener.onLightningClick());
        practise = root.findViewById(R.id.practise);
        practise.setOnClickListener(v -> listener.onJoystickClick());
        random = root.findViewById(R.id.random);
        random.setOnClickListener(v -> listener.onDiceClick());
        leaderboard = root.findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(v -> listener.onLeaderboardClick());
        user = root.findViewById(R.id.user);
        user.setOnClickListener(v -> listener.onUserClick());
    }

    @Override
    public void setMenuClickListener(SymbolClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setUserIconToSignOut() {
        user.setImageResource(R.drawable.sign_out_pink);
    }

    @Override
    public void setUserIconToDefault() {
        user.setImageResource(R.drawable.user_pink);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
