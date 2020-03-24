package sk3m3l1io.kalymnos.memogame.view;

public interface GameScreenView extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface SymbolClickListener {
        void onSymbolClick(int position);
    }

    void setSymbolClickListener(SymbolClickListener listener);

    void setCover(String cover);

    void setSymbol(int position, String symbol);

    String getSymbol(int position);

    void disableSymbol(int position);
}
