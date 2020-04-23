package sk3m3l1io.duisburg.memogame.controller.menu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;

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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.controller.game.GameActivity;
import sk3m3l1io.duisburg.memogame.controller.stats.LeaderBoardActivity;
import sk3m3l1io.duisburg.memogame.model.pojos.Game;
import sk3m3l1io.duisburg.memogame.model.pojos.GameMode;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.repos.FirebaseScoreRepository;
import sk3m3l1io.duisburg.memogame.model.repos.GameDataRepository;
import sk3m3l1io.duisburg.memogame.model.repos.GameDataRepositoryImp;
import sk3m3l1io.duisburg.memogame.utils.GoogleUtils;
import sk3m3l1io.duisburg.memogame.view.menu.MainView;
import sk3m3l1io.duisburg.memogame.view.menu.MainViewImp;

public class MainActivity extends AppCompatActivity implements
        MenuFragment.MenuItemClickListener,
        MenuFragment.OnViewCreationListener,
        GameBriefingFragment.OnPlayClickListener,
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
        // TODO: check from the start if the user is already signed in and update the UI
        googleSignInClient = GoogleUtils.getSignInClient(this);
    }

    private void addMenuFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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
                    Game.class.getSimpleName(), (ArrayList<? extends Parcelable>) games);
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
                            GameDataRepository repo = new GameDataRepositoryImp(getAssets().open(p));
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
            new FirebaseScoreRepository().savePlayer(GoogleUtils.createPlayerFrom(acc));
            Snackbar.make(view.getRootView(), R.string.progress_can_be_saved_msg, Snackbar.LENGTH_SHORT).show();
        } catch (ApiException e) {
            updateMenuUI(null);
        }
    }

    @Override
    public void onMenuFragmentViewCreated() {
        updateMenuUI(GoogleSignIn.getLastSignedInAccount(this));
    }

    private void updateMenuUI(GoogleSignInAccount acc) {
        int id = view.getMenuContainerId();
        MenuFragment f = (MenuFragment) getSupportFragmentManager().findFragmentById(id);
        if (f != null && acc != null) {
            f.setSignOutUI();
            if (acc.getDisplayName() != null) {
                view.setPlayerName(getString(R.string.player_name_prefix) + " " + acc.getDisplayName());
                view.showPlayerName();
            }
        } else {
            f.setSignInUI();
        }
    }

    @Override
    public void onTimeModeClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            forwardSound.start();
            addBriefingFragmentOf(gameMode = GameMode.TIME);
        } else {
            showSignInSnackbar();
        }
    }

    @Override
    public void onSurvivalModeClick() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            forwardSound.start();
            addBriefingFragmentOf(gameMode = GameMode.SURVIVAL);
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
        addBriefingFragmentOf(gameMode);
    }

    private void addBriefingFragmentOf(GameMode mode) {
        GameBriefingFragment f = GameBriefingFragment.instanceOf(mode);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(view.getMenuContainerId(), f)
                .commit();
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
        Player p = GoogleUtils.createPlayerFrom(GoogleSignIn.getLastSignedInAccount(this));
        Intent i = new Intent(this, LeaderBoardActivity.class);
        i.putExtra(Player.class.getSimpleName(), p);
        startActivity(i);
    }

    @Override
    public void onPlayClick() {
        forwardSound.start();
        startActivity(GameActivity.createIntent(this, gameMode, getPlayData()));
    }

    private Bundle getPlayData() {
        Bundle data = new Bundle();
        data.putParcelableArrayList(Game.class.getSimpleName(), (ArrayList<Game>) games);
        data.putSerializable(GameMode.class.getSimpleName(), gameMode);
        Player p = GoogleUtils.createPlayerFrom(GoogleSignIn.getLastSignedInAccount(this));
        data.putParcelable(Player.class.getSimpleName(), p);
        return data;
    }

    @Override
    public void onSuccess(Void aVoid) {
        updateUiOnSignOut();
    }

    private void updateUiOnSignOut() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        ((MenuFragment) f).setSignInUI();
        view.hidePlayerName();
        Snackbar.make(view.getRootView(), R.string.sign_out_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(view.getMenuContainerId());
        if (f != null && f instanceof GameBriefingFragment) {
            backwardSound.start();
        }
        super.onBackPressed();
    }
}
