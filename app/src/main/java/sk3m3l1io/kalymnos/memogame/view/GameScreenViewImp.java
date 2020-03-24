package sk3m3l1io.kalymnos.memogame.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sk3m3l1io.kalymnos.memogame.R;

public class GameScreenViewImp implements GameScreenView {
    private View root;
    private TextView[] symbols;
    private SymbolClickListener symbolClickListener;

    public GameScreenViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.activity_game, container, false);
        initSymbols(inflater);
    }

    private void initSymbols(LayoutInflater inflater) {
        symbols = new TextView[SYMBOL_COUNT];
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

        setOnClickListenerToSymbols(symbols);
    }

    private void setOnClickListenerToSymbols(TextView[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            TextView symbol = symbols[i];
            int position = i;
            symbol.setOnClickListener(s -> {
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
    public void setCover(char cover) {
        for (TextView t : symbols) {
            t.setText("" + cover);
        }
    }

    @Override
    public void setSymbol(int symbolPosition, char symbol) {
        symbols[symbolPosition].setText(""+symbol);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
