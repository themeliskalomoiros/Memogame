package sk3m3l1io.kalymnos.memogame.view;

public interface GameScreen extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface SymbolClickListener {
        void onSymbolClick(int position);
    }

    void setSymbolClickListener(SymbolClickListener listener);

    void coverAllSymbolsWith(String cover);

    void setSymbolValue(int position, String symbol);

    String getSymbolValue(int position);

    void disableSymbol(int position);

    void disableAllSymbols();

    void setTitle(String title);

    void setTime(String time);
}
