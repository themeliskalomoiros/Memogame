package sk3m3l1io.kalymnos.memogame.view.menu;

import sk3m3l1io.kalymnos.memogame.view.ViewMvc;

public interface MenuItemDetailsView extends ViewMvc {
    interface PlayClickListener{
        void onPlayClick();
    }

    void setPlayClickListener(PlayClickListener listener);

    void setTitle(String title);

    void setMessage(String message);
}
