package sk3m3l1io.duisburg.memogame.view.menu;

import sk3m3l1io.duisburg.memogame.view.TitleView;

public interface GameBriefingView extends TitleView {
    interface StartGameClickListener {
        void onStartGameClick();
    }

    void setStartGameClickListener(StartGameClickListener listener);

    void setMessage(String m);
}
