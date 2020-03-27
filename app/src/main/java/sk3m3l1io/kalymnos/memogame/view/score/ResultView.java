package sk3m3l1io.kalymnos.memogame.view.score;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface ResultView extends ViewMvc {
    interface ButtonClickListener{
        void onButtonClick();
    }
    void setButtonClickListener(ButtonClickListener listener);
    void setTitle(String title);
    void setMessage(String message);
    void setGameDetails(String text);
    void setScore(String score);
    void hideDetails();
}
