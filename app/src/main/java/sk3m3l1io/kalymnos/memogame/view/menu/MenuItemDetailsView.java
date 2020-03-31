package sk3m3l1io.kalymnos.memogame.view.menu;

import sk3m3l1io.kalymnos.memogame.view.TitleView;
import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface MenuItemDetailsView extends TitleView {
    interface ButtonClickListener {
        void onButtonClick();
    }

    void setButtonClickListener(ButtonClickListener listener);

    void setMessage(String m);
}
