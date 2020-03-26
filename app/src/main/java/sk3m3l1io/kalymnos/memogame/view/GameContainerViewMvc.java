package sk3m3l1io.kalymnos.memogame.view;

public interface GameContainerViewMvc extends ViewMvc {
    void setTitle(String title);

    void setTimeProgress(int progress);

    void setTimeMaxProgress(int progress);

    int getGameContainerId();
}
