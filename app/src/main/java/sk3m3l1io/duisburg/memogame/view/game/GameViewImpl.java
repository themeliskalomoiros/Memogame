package sk3m3l1io.duisburg.memogame.view.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sk3m3l1io.duisburg.memogame.R;

public class GameViewImpl implements GameView {
    private final View root;
    private final Button[] symbols;
    private SymbolClickListener symbolClickListener;

    public GameViewImpl(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_game, container, false);
        symbols = new Button[SYMBOL_COUNT];
        populateSymbols(inflater);
    }

    private void populateSymbols(LayoutInflater inflater) {
        symbols[0] = root.findViewById(R.id.symbol_0);
        symbols[1] = root.findViewById(R.id.symbol_1);
        symbols[2] = root.findViewById(R.id.symbol_2);
        symbols[3] = root.findViewById(R.id.symbol_3);
        symbols[4] = root.findViewById(R.id.symbol_4);
        symbols[5] = root.findViewById(R.id.symbol_5);
        symbols[6] = root.findViewById(R.id.symbol_6);
        symbols[7] = root.findViewById(R.id.symbol_7);
        symbols[8] = root.findViewById(R.id.symbol_8);
        symbols[9] = root.findViewById(R.id.symbol_9);
        symbols[10] = root.findViewById(R.id.symbol_10);
        symbols[11] = root.findViewById(R.id.symbol_11);

        addClickListenerTo(symbols);
    }

    private void addClickListenerTo(Button[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            int pos = i;
            symbols[i].setOnClickListener(v -> symbolClickListener.onSymbolClick(pos));
        }
    }

    @Override
    public void setSymbolClickListener(SymbolClickListener listener) {
        symbolClickListener = listener;
    }

    @Override
    public void coverAllSymbols(String cover) {
        for (Button b : symbols)
            b.setText(cover);
    }

    @Override
    public void setSymbolForeground(int pos, int colorRes) {
        Button b = symbols[pos];
        b.setTextColor(colorRes);
    }

    @Override
    public void setAllSymbolsBackground(int colorRes) {
        for (Button b : symbols)
            b.setBackgroundColor(colorRes);
    }

    @Override
    public void setSymbol(int pos, String s) {
        Button b = symbols[pos];
        b.setText(s);
    }

    @Override
    public void setAllSymbols(String[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            Button b = this.symbols[i];
            b.setText(symbols[i]);
        }
    }

    @Override
    public void disableSymbol(int pos) {
        Button b = symbols[pos];
        b.setEnabled(false);
    }

    @Override
    public void disableAllSymbols() {
        for (Button b : symbols)
            b.setEnabled(false);
    }

    @Override
    public void enableAllSymbols() {
        for (Button b : symbols)
            b.setEnabled(true);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
