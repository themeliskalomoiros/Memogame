package sk3m3l1io.kalymnos.memogame.view;

public interface GameScreen extends ViewMvc {
    int SYMBOL_COUNT = 12;

    interface ClickListener {
        void onPreviousClick();

        void onNextClick();

        void onSymbolClick(int pos);
    }

    void setClickListener(ClickListener listener);

    void setSymbolColor(int pos, int colorRes);

    void setAllSymbolsColor(int color);

    void setAllSymbolsBackgroundColor(int colorRes);

    void setAllSymbolsBackgroundToDefault();

    void setSymbolValue(int pos, String value);

    void setAllSymbolsValue(String cover);

    void disableSymbol(int pos);

    void enableAllSymbols();

    void disableAllSymbols();

    void setTitle(String title);

    void setTimeProgress(int progress);

    void setTimeMaxProgress(int progress);
}
