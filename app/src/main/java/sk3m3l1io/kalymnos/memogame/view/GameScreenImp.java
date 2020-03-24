package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;

public class GameScreenImp implements GameScreen {
    private final View root;
    private final TextView title, time;
    private final ProgressBar progressBar;
    private final Button[] symbols;
    private SymbolClickListener symbolClickListener;

    public GameScreenImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_game, container, false);
        progressBar = root.findViewById(R.id.progressbar);
        title = root.findViewById(R.id.title);
        time = root.findViewById(R.id.time);
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
            Button b = symbols[i];
            int pos = i;
            b.setOnClickListener(v -> symbolClickListener.onSymbolClick(pos));
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
    public void setSymbolBackground(int position, int colorRes) {
        Button b = symbols[position];
        b.setBackgroundColor(colorRes);
    }

    @Override
    public void setAllSymbolsBackground(int colorRes) {
        for (Button b : symbols)
            b.setBackgroundColor(colorRes);
    }

    @Override
    public void setSymbolValue(int position, String symbol) {
        Button b = symbols[position];
        b.setText(symbol);
    }

    @Override
    public void disableSymbol(int position) {
        Button b = symbols[position];
        b.setEnabled(false);
    }

    @Override
    public void disableAllSymbols() {
        for (Button b : symbols)
            b.setEnabled(false);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTimeProgress(int value) {
        progressBar.setProgress(value);
    }

    @Override
    public void setTimeProgressMax(int value) {
        progressBar.setMax(value);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
