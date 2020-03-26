package sk3m3l1io.kalymnos.memogame.view;

public interface GameView extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface SymbolClickListener {
        void onSymbolClick(int pos);
    }

    void setSymbolClickListener(SymbolClickListener listener);

    void setSymbolColor(int pos, int colorRes);

    void setAllSymbolsColor(int color);

    void setAllSymbolsBackgroundColor(int colorRes);

    void setAllSymbolsBackgroundToDefault();

    void setSymbolValue(int pos, String value);

    void setAllSymbolsValue(String cover);

    void disableSymbol(int pos);

    void enableAllSymbols();

    void disableAllSymbols();
}
