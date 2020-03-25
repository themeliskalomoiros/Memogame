package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sk3m3l1io.kalymnos.memogame.model.GameRepository;
import sk3m3l1io.kalymnos.memogame.model.GameRepositoryImp;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.view.MainScreen;
import sk3m3l1io.kalymnos.memogame.view.MainScreenImp;

public class MainActivity extends AppCompatActivity implements
        MainScreen.PlayClickListener,
        LoaderManager.LoaderCallbacks<List<Game>> {

    private static final int LOADER_ID = 123;

    private MainScreen view;
    private List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainScreenImp(getLayoutInflater(), null);
        view.setPlayClickListener(this);
        setContentView(view.getRootView());
        if (gamesExistIn(savedInstanceState)) {
            games = savedInstanceState.getParcelableArrayList(Game.class.getSimpleName());
        } else {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    private boolean gamesExistIn(Bundle savedInstanceState) {
        return savedInstanceState != null &&
                savedInstanceState.containsKey(Game.class.getSimpleName());
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
    public void onPlayClick() {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra(Game.class.getSimpleName(), (ArrayList<Game>) games);
        startActivity(i);
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
                return getGames();
            }

            private List<Game> getGames() {
                try (AssetManager assets = getAssets()) {
                    String[] paths = assets.list("");
                    for (String p : paths) {
                        if (p.contains("games.json")) {
                            GameRepository repo = new GameRepositoryImp(assets.open(p));
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
}
