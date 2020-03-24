package sk3m3l1io.kalymnos.memogame.view;

public interface PlayScreenView extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface SymbolClickListener{
        void onSymbolClick(int position);
    }

    void setSymbolClickListener(SymbolClickListener listener);

    void setAllSymbols(String symbol);
}
