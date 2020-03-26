package sk3m3l1io.kalymnos.memogame.view;

public interface PractiseView extends GameContainerViewMvc {
    interface ChangeGameClickListener {
        void onPreviousGameClick();

        void onNextGameClick();
    }

    void setChangeGameClickListener(ChangeGameClickListener listener);
}
