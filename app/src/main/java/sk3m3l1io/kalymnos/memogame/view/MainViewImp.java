package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;

public class MainViewImp implements MainView {
    private View root;
    private TextView name;
    private ImageButton practise, signIn, hotRound, leaderBoard;
    private ProgressBar loadingBar;
    private ClickListener clickListener;

    public MainViewImp(LayoutInflater inflater, ViewGroup container) {
        initViews(inflater, container);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_main, container, false);
        name = root.findViewById(R.id.player_name);
        hotRound = root.findViewById(R.id.hot_round);
        hotRound.setOnClickListener(v -> clickListener.onHotRoundClick());
        practise = root.findViewById(R.id.practise);
        practise.setOnClickListener(v -> clickListener.onPractiseClick());
        signIn = root.findViewById(R.id.sign_in);
        signIn.setOnClickListener(v -> clickListener.onSignInClick());
        leaderBoard = root.findViewById(R.id.leaderboard);
        leaderBoard.setOnClickListener(v -> clickListener.onLeaderBoardClick());
        loadingBar = root.findViewById(R.id.progressbar);
    }

    @Override
    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }

    @Override
    public void showLoadingIndicator() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void setSignInButtonImage(int imageRes) {
        signIn.setImageResource(imageRes);
    }

    @Override
    public void setPlayerName(String name) {
        this.name.setText(name);
    }

    @Override
    public void showPlayerName() {
        name.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlayerName() {
        name.setVisibility(View.GONE);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
