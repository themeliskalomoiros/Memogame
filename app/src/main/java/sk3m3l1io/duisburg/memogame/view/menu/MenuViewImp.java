package sk3m3l1io.duisburg.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sk3m3l1io.duisburg.memogame.R;

public class MenuViewImp implements MenuView {
    private View root;
    private Button lightning, random, leaderboard, user;
    private Button practise;
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
        user.setCompoundDrawables(root.getResources().getDrawable(R.drawable.sign_out_pink), null, null, null);
    }

    @Override
    public void setUserIconToDefault() {
        user.setCompoundDrawables(root.getResources().getDrawable(R.drawable.user_green), null, null, null);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
