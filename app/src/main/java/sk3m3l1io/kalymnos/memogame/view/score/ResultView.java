package sk3m3l1io.kalymnos.memogame.view.score;

import sk3m3l1io.kalymnos.memogame.view.TitleView;
import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

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
