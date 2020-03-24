package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sk3m3l1io.kalymnos.memogame.R;

public class GameScreenViewImp implements GameScreenView {
    private View root;

    public GameScreenViewImp(LayoutInflater inflater, ViewGroup container) {
        initViews(inflater, container);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_game, container, false);
    }

    @Override
    public void setSymbolClickListener(SymbolClickListener listener) {

    }

    @Override
    public void setAllSymbols(String symbol) {

    }

    @Override
    public View getRootView() {
        return root;
    }
}
