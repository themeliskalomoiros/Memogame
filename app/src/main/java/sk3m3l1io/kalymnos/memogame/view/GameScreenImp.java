package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;

public class GameScreenImp implements GameScreen {
    private View root;
    private TextView title, time;
    private SymbolView[] symbols;
    private SymbolClickListener symbolClickListener;

    public GameScreenImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_game, container, false);
        title = root.findViewById(R.id.title);
        time = root.findViewById(R.id.time);
        initSymbols(inflater);
    }

    private void initSymbols(LayoutInflater inflater) {
        symbols = new SymbolView[SYMBOL_COUNT];
        symbols[0] = new SymbolView(root.findViewById(R.id.symbol_0));
        symbols[1] = new SymbolView(root.findViewById(R.id.symbol_1));
        symbols[2] = new SymbolView(root.findViewById(R.id.symbol_2));
        symbols[3] = new SymbolView(root.findViewById(R.id.symbol_3));
        symbols[4] = new SymbolView(root.findViewById(R.id.symbol_4));
        symbols[5] = new SymbolView(root.findViewById(R.id.symbol_5));
        symbols[6] = new SymbolView(root.findViewById(R.id.symbol_6));
        symbols[7] = new SymbolView(root.findViewById(R.id.symbol_7));
        symbols[8] = new SymbolView(root.findViewById(R.id.symbol_8));
        symbols[9] = new SymbolView(root.findViewById(R.id.symbol_9));
        symbols[10] = new SymbolView(root.findViewById(R.id.symbol_10));
        symbols[11] = new SymbolView(root.findViewById(R.id.symbol_11));

        setOnClickListenerToSymbols(symbols);
    }

    private void setOnClickListenerToSymbols(SymbolView[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            ViewGroup c = symbols[i].container;
            int position = i;
            c.setOnClickListener(s -> {
                if (symbolClickListener != null)
                    symbolClickListener.onSymbolClick(position);
            });
        }
    }

    @Override
    public void setSymbolClickListener(SymbolClickListener listener) {
        symbolClickListener = listener;
    }

    @Override
    public void setCover(String cover) {
        for (SymbolView v : symbols)
            v.symbol.setText(cover);
    }

    @Override
    public void setSymbol(int position, String symbol) {
        symbols[position].symbol.setText(symbol);
    }

    @Override
    public String getSymbol(int position) {
        return symbols[position].symbol.getText().toString();
    }

    @Override
    public void disableSymbol(int position) {
        symbols[position].container.setEnabled(false);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTime(String time) {
        this.time.setText(time);
    }

    @Override
    public View getRootView() {
        return root;
    }

    private class SymbolView {
        ViewGroup container;
        TextView symbol;

        SymbolView(ViewGroup container) {
            this.container = container;
            symbol = (TextView) container.getChildAt(0);
        }
    }
}
