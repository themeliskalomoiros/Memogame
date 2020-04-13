package sk3m3l1io.duisburg.memogame.controller.menu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.controller.stats.LeaderBoardActivity;
import sk3m3l1io.duisburg.memogame.controller.game.ArcadeActivity;
import sk3m3l1io.duisburg.memogame.controller.game.PractiseModeActivity;
import sk3m3l1io.duisburg.memogame.controller.game.RandomModeActivity;
import sk3m3l1io.duisburg.memogame.model.GameRepository;
import sk3m3l1io.duisburg.memogame.model.GameRepositoryImp;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.pojos.Player;
import sk3m3l1io.duisburg.memogame.utils.GoogleUtils;
import sk3m3l1io.duisburg.memogame.utils.LogUtils;
import sk3m3l1io.duisburg.memogame.view.menu.MainView;
import sk3m3l1io.duisburg.memogame.view.menu.MainViewImp;

public class MainActivity extends AppCompatActivity implements
        MenuFragment.MenuItemClickListener,
        MenuFragment.ViewCreationListener,
        GameBriefingFragment.StartGameClickListener,
        LoaderManager.LoaderCallbacks<List<Game>>,
        OnSuccessListener<Void> {
    private static final int LOADER_ID = 123;
    private static final int RC_SIGN_IN = 158;

    private MainView view;
    private GameMode gameMode;
    private List<Game> games;
    private GoogleSignInClient googleSignInClient;
    private MediaPlayer forwardSound, backwardSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainViewImp(getLayoutInflater(), null);
        setContentView(view.getRootView());
        addMenuFragment();
        loadGames(savedInstanceState);
        initGoogleSignInClient();
    }

    private void addMenuFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(view.getMenuContainerId(), new MenuFragment())
                .commit();
    }

    private void loadGames(Bundle savedInstanceState) {
        boolean gamesExist = savedInstanceState != null &&
                savedInstanceState.containsKey(Game.class.getSimpleName());
        if (gamesExist) {
            games = savedInstanceState.getParcelableArrayList(Game.class.getSimpleName());
        } else {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        forwardSound = MediaPlayer.create(this, R.raw.navigation_forward_minimal);
        backwardSound = MediaPlayer.create(this, R.raw.navigation_backward_minimal);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (games != null && games.size() >= 0) {
            outState.putParcelableArrayList(
                    Game.class.getSimpleName(),
                    (ArrayList<? extends Parcelable>) games);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        forwardSound.release();
        backwardSound.release();
    }

    @NonNull
    @Override
    public Loader<List<Game>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Game>>(this) {

            @Override
            protected void onStartLoading() {
                if (games == null) {
                    view.showLoadingIndicator();
                    view.getRootView().setEnabled(false);
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<Game> loadInBackground() {
                try {
                    String[] paths = getAssets().list("");
                    for (String p : paths) {
                        if (p.contains("games.json")) {
                            GameRepository repo = new GameRepositoryImp(getAssets().open(p));
                            return repo.getGames();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Game>> loader, List<Game> data) {
        view.hideLoadingIndicator();
        view.getRootView().setEnabled(true);
        games = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Game>> loader) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            updateMenuUI(acc);
            Snackbar.make(view.getRootView(), R.string.sign_in_msg, Snackbar.LENGTH_SHORT).show();
        } catch (ApiException e) {
            updateMenuUI(null);
        }
    }

    @Override
    public void onMenuViewCreation() {
        updateMenuUI(GoogleSignIn.getLastSignedInAccount(this));
    }

    private void updateMenuUI(GoogleSignInAccount acc) {
        MenuFragment f = (MenuFragment) getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        if (f != null && acc != null) {
            f.setSignOutIcon();
            if (acc.getDisplayName() != null) {
                view.setPlayerName(getString(R.string.player_name_prefix) + " " + acc.getDisplayName());
                view.showPlayerName();
            }
        } else {
            f.setDefaultSignInIcon();
        }
    }

    @Override
    public void onArcadeModeClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            addGameBriefingFragment(gameMode = GameMode.ARCADE);
        } else {
            showSignInSnackbar();
        }
    }

    @Override
    public void onRandomModeClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            addGameBriefingFragment(gameMode = GameMode.RANDOM);
        } else {
            showSignInSnackbar();
        }
    }

    private void showSignInSnackbar() {
        Snackbar.make(view.getRootView(), R.string.must_sign_in, Snackbar.LENGTH_LONG)
                .setAction(R.string.sign_in, v -> onSignInClick())
                .show();
    }

    @Override
    public void onPractiseModeClick() {
        forwardSound.start();
        gameMode = GameMode.PRACTISE;
        addGameBriefingFragment(gameMode);
    }

    @Override
    public void onSignInClick() {
        forwardSound.start();
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            googleSignInClient.signOut().addOnSuccessListener(this);
        } else {
            startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
        }
    }

    @Override
    public void onLeaderboardClick() {
        forwardSound.start();
        startLeaderBoardActivity();
    }

    private void startLeaderBoardActivity() {
        Intent i = new Intent(this, LeaderBoardActivity.class);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        Player p = GoogleUtils.createPlayerFrom(acc);
        if (p != null)
            i.putExtra(Player.class.getSimpleName(), p);
        startActivity(i);
    }

    @Override
    public void onStartGameClick() {
        forwardSound.start();
        startActivity(getPlayModeIntent(gameMode));
    }

    private Intent getPlayModeIntent(GameMode mode) {
        Intent i = new Intent();
        i.putExtra(Game.class.getSimpleName(), (ArrayList<Game>) games);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        Player p = GoogleUtils.createPlayerFrom(acc);
        i.putExtra(Player.class.getSimpleName(), p);

        switch (mode) {
            case ARCADE:
                i.setClass(this, ArcadeActivity.class);
                break;
            case PRACTISE:
                i.setClass(this, PractiseModeActivity.class);
                break;
            case RANDOM:
                i.setClass(this, RandomModeActivity.class);
                break;
        }

        return i;
    }

    @Override
    public void onSuccess(Void aVoid) {
        updateUiOnSignOut();
    }

    private void updateUiOnSignOut() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        ((MenuFragment) f).setDefaultSignInIcon();
        view.hidePlayerName();
        Snackbar.make(view.getRootView(), R.string.sign_out_success, Snackbar.LENGTH_LONG).show();
    }

    private void addGameBriefingFragment(GameMode mode) {
        GameBriefingFragment f = GameBriefingFragment.instanceOf(mode);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(view.getMenuContainerId(), f)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        if (f!=null && f instanceof GameBriefingFragment){
            backwardSound.start();
        }
        super.onBackPressed();
    }
}
