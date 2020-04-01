package sk3m3l1io.duisburg.memogame.view.menu;

import sk3m3l1io.duisburg.memogame.view.TitleView;

public interface MenuItemDetailsView extends TitleView {
    interface ButtonClickListener {
        void onButtonClick();
    }

    void setButtonClickListener(ButtonClickListener listener);

    void setMessage(String m);
}
