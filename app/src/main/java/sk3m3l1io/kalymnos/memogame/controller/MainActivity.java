package sk3m3l1io.kalymnos.memogame.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import sk3m3l1io.kalymnos.memogame.model.GameRepository;
import sk3m3l1io.kalymnos.memogame.model.GameRepositoryImp;
import sk3m3l1io.kalymnos.memogame.pojos.Game;
import sk3m3l1io.kalymnos.memogame.utils.ArrayUtils;
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
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onPlayClick() {
        Intent i = new Intent(this, GameActivity.class);
        int randomIndex = new Random().nextInt(games.size());
        Game game = games.get(randomIndex);
        ArrayUtils.shuffle(game.getSymbols());
        i.putExtra(Game.class.getSimpleName(), game);
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
