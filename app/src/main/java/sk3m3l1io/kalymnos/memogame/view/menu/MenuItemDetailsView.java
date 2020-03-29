package sk3m3l1io.kalymnos.memogame.view.menu;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface MenuItemDetailsView extends ViewMvc {
    interface ButtonClickListener {
        void onButtonClick();
    }

    void setPlayClickListener(ButtonClickListener listener);

    void setTitle(String title);

    void setMessage(String message);
}
