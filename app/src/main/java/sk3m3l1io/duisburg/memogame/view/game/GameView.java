package sk3m3l1io.duisburg.memogame.view.game;

import sk3m3l1io.duisburg.memogame.view.ViewMvc;

public interface GameView extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface SymbolClickListener {

        void onSymbolClick(int pos);
    }

    void setSymbolClickListener(SymbolClickListener listener);

    void setSymbolForeground(int pos, int colorRes);

    void setAllSymbolsBackground(int colorRes);

    void setSymbol(int pos, String s);

    void setAllSymbols(String[] symbols);

    void coverAllSymbols(String cover);

    void disableSymbol(int pos);

    void disableAllSymbols();

    void enableAllSymbols();
}
