package sk3m3l1io.kalymnos.memogame.view;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface TimeView extends ViewMvc {
    void setTimeProgress(int progress);

    void setTimeProgressMax(int progress);
}
