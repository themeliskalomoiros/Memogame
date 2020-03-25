package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.model.GameRepository;
import sk3m3l1io.kalymnos.memogame.model.GameRepositoryImp;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.view.MainScreen;
import sk3m3l1io.kalymnos.memogame.view.MainScreenImp;

public class MainActivity extends AppCompatActivity implements
        MainScreen.ClickListener,
        LoaderManager.LoaderCallbacks<List<Game>>, OnCanceledListener, OnFailureListener, OnSuccessListener<Void> {

    private static final int LOADER_ID = 123;
    private static final int RC_SIGN_IN = 158;

    private MainScreen view;
    private List<Game> games;

    private GoogleSignInClient googleSignInClient;
    private boolean userInformedAboutSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainScreenImp(getLayoutInflater(), null);
        view.setClickListener(this);
        setContentView(view.getRootView());
        loadGames(savedInstanceState);
        initGoogleSignInClient();
    }

    private void loadGames(Bundle savedInstanceState) {
        if (gamesExistIn(savedInstanceState)) {
            games = savedInstanceState.getParcelableArrayList(Game.class.getSimpleName());
        } else {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private boolean gamesExistIn(Bundle savedInstanceState) {
        return savedInstanceState != null &&
                savedInstanceState.containsKey(Game.class.getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            view.setSignInButtonImage(R.drawable.sign_out_48px);
            view.setPlayerName(account.getDisplayName());
            view.showPlayerName();
            Snackbar.make(view.getRootView(),R.string.sign_in_msg,Snackbar.LENGTH_SHORT).show();
        } else {
            view.setSignInButtonImage(R.drawable.google_48px);
            if(!userInformedAboutSignIn){
                Snackbar.make(view.getRootView(),R.string.prompt_for_sign_in_msg,Snackbar.LENGTH_SHORT).show();
                userInformedAboutSignIn = true;
            }
        }
    }

    @Override
    public void onPlayClick() {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(Game.class.getSimpleName(), (ArrayList<Game>) games);
        startActivity(i);
    }

    @Override
    public void onSignInClick() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            googleSignInClient.signOut()
                    .addOnCanceledListener(this)
                    .addOnFailureListener(this)
                    .addOnSuccessListener(this);
        } else {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
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
    public void onCanceled() {
        Snackbar.make(view.getRootView(), R.string.sign_out_cancel, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Snackbar.make(view.getRootView(), R.string.sign_out_fail, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Void aVoid) {
        Snackbar.make(view.getRootView(), R.string.sign_out_success,Snackbar.LENGTH_LONG).show();
        view.setSignInButtonImage(R.drawable.google_48px);
        view.hidePlayerName();
    }
}
