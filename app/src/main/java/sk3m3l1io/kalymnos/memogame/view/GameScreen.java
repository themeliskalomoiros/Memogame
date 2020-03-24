package sk3m3l1io.kalymnos.memogame.view;

public interface GameScreen extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface SymbolClickListener {

        void onSymbolClick(int position);
    }
    void setSymbolClickListener(SymbolClickListener listener);

    void coverAllSymbols(String cover);

    void setSymbolColor(int position, int color);

    void setAllSymbolsColor(int color);

    void setSymbolBackgroundColor(int position, int colorRes);

    void setAllSymbolsBackgroundColor(int colorRes);

    void setSymbolValue(int position, String symbol);

    void disableSymbol(int position);

    void disableAllSymbols();

    void setTitle(String title);

    void setTimeProgress(int value);

    void setTimeProgressMax(int value);
}
