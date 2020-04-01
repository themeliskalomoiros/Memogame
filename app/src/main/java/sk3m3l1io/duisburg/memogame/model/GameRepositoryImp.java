package sk3m3l1io.duisburg.memogame.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sk3m3l1io.duisburg.memogame.pojos.Game;
import sk3m3l1io.duisburg.memogame.pojos.GameDifficulty;
import sk3m3l1io.duisburg.memogame.utils.FileUtils;

public class GameRepositoryImp implements GameRepository {
    private static final String KEY_TITLE = "title";
    private static final String KEY_COVER = "cover";
    private static final String KEY_SYMBOLS = "symbols";
    private static final String KEY_DIFFICULTY = "difficulty";


    private final String json;

    public GameRepositoryImp(final InputStream file) {
        json = FileUtils.getTextFrom(file);
    }

    @Override
    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();

        try {
            JSONObject jRoot = new JSONObject(json);
            JSONArray gamesArray = jRoot.getJSONArray("games");
            for (int i = 0; i < gamesArray.length(); i++) {
                JSONObject gameObj = gamesArray.getJSONObject(i);
                Game game = extractGameFrom(gameObj);
                games.add(game);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return games;
    }

    private Game extractGameFrom(JSONObject gameObj) throws JSONException {
        String t = gameObj.getString(KEY_TITLE);
        String c = gameObj.getString(KEY_COVER);
        GameDifficulty d = getDifficultyFrom(gameObj.getString(KEY_DIFFICULTY));
        JSONArray array = gameObj.getJSONArray(KEY_SYMBOLS);
        List<String> symbols = getSymbolsFrom(array);
        return new Game(t, c, symbols.toArray(new String[symbols.size()]), d);
    }

    private GameDifficulty getDifficultyFrom(String string) {
        switch (string) {
            case "easy":
                return GameDifficulty.EASY;
            case "normal":
                return GameDifficulty.NORMAL;
            default:
                return GameDifficulty.HARD;
        }
    }

    private List<String> getSymbolsFrom(JSONArray symbolsArray) throws JSONException {
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < symbolsArray.length(); j++) {
            String symbol = symbolsArray.getString(j);
            symbols.add(symbol);
        }
        return symbols;
    }
}
