package sk3m3l1io.duisburg.memogame.view.score;

import sk3m3l1io.duisburg.memogame.view.TitleView;

public interface ResultView extends TitleView {
    interface ButtonClickListener {
        void onButtonClick();
    }

    void setButtonClickListener(ButtonClickListener listener);

    void setMessage(String m);

    void setGameDetails(String d);

    void setScore(String s);

    void hideDetails();
}
