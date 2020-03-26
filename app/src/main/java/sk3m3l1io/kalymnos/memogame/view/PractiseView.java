package sk3m3l1io.kalymnos.memogame.view;

public interface PractiseView extends ViewMvc {
    interface ChangeGameClickListener {
        void onPreviousGameClick();

        void onNextGameClick();
    }

    void setChangeGameClickListener(ChangeGameClickListener listener);

    void setTitle(String title);

    void setTimeProgress(int progress);

    void setTimeMaxProgress(int progress);

    int getGameContainerId();
}
