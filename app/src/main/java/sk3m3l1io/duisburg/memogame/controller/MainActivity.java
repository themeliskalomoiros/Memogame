package sk3m3l1io.duisburg.memogame.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import sk3m3l1io.duisburg.memogame.model.GameRepository;
import sk3m3l1io.duisburg.memogame.model.GameRepositoryImp;
import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.pojos.Player;
import sk3m3l1io.duisburg.memogame.utils.GoogleUtils;
import sk3m3l1io.duisburg.memogame.view.menu.MainView;
import sk3m3l1io.duisburg.memogame.view.menu.MainViewImp;

public class MainActivity extends AppCompatActivity
        implements MenuFragment.MenuItemClickListener,
        MenuItemDetailsFragment.PlayClickListener,
        LoaderManager.LoaderCallbacks<List<Game>>,
        OnSuccessListener<Void> {
    private static final int LOADER_ID = 123;
    private static final int RC_SIGN_IN = 158;

    private MainView view;
    private GameMode gameMode;
    private List<Game> games;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainViewImp(getLayoutInflater(), null);
        setContentView(view.getRootView());
        loadGames(savedInstanceState);
        initGoogleSignInClient();
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
        boolean fragmentDontExist = !getSupportFragmentManager().popBackStackImmediate();
        if (fragmentDontExist) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(view.getMenuContainerId(), new MenuFragment())
                    .commitNow();
            updateMenuUI(GoogleSignIn.getLastSignedInAccount(this));
        }
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

    private void updateMenuUI(GoogleSignInAccount acc) {
        MenuFragment f = (MenuFragment) getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        if (f != null && acc != null) {
            f.setSignOutIcon();
            if (acc.getDisplayName() != null) {
                view.setPlayerName(acc.getDisplayName());
                view.showPlayerName();
            }
        } else {
            f.setDefaultSignInIcon();
        }
    }

    @Override
    public void onArcadeModeClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            gameMode = GameMode.ARCADE;
            addMenuItemDetailsFragmentToBackStack(gameMode);
        } else {
            Snackbar.make(view.getRootView(), R.string.must_sign_in, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRandomModeClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            gameMode = GameMode.RANDOM;
            addMenuItemDetailsFragmentToBackStack(gameMode);
        } else {
            Snackbar.make(view.getRootView(), R.string.must_sign_in, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPractiseModeClick() {
        gameMode = GameMode.PRACTISE;
        addMenuItemDetailsFragmentToBackStack(gameMode);
    }

    private void addMenuItemDetailsFragmentToBackStack(GameMode mode) {
        MenuItemDetailsFragment f = MenuItemDetailsFragment.getInstanceOf(mode);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(view.getMenuContainerId(), f)
                .commit();
    }

    @Override
    public void onSignInClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            googleSignInClient.signOut().addOnSuccessListener(this);
        } else {
            startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
        }
    }

    @Override
    public void onLeaderboardClick() {
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        Player p = GoogleUtils.createPlayerFrom(acc);
        if (p != null) {
            Intent i = new Intent(this, LeaderBoardActivity.class);
            i.putExtra(Player.class.getSimpleName(), p);
            startActivity(i);
        } else {
            Snackbar.make(view.getRootView(), R.string.must_sign_in, Snackbar.LENGTH_LONG).show();
        }
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
                Log.d(MainActivity.class.getSimpleName(), "loadingInBackground() called");
                return getGames();
            }

            private List<Game> getGames() {
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
    public void onSuccess(Void aVoid) {
        MenuFragment f = (MenuFragment) getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        f.setDefaultSignInIcon();
        view.hidePlayerName();
        Snackbar.make(view.getRootView(), R.string.sign_out_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onPlayClick() {
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
}
