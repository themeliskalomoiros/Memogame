package sk3m3l1io.kalymnos.memogame.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sk3m3l1io.kalymnos.memogame.R;

public class MenuItemDetailsViewImp implements MenuItemDetailsView {
    private View root;
    private FloatingActionButton play;
    private TextView title, message;
    private MenuItemDetailsView.PlayClickListener listener;

    public MenuItemDetailsViewImp(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_menu_item_details, container, false);
        play = root.findViewById(R.id.fab);
        play.setOnClickListener(v -> listener.onPlayClick());
        title = root.findViewById(R.id.title);
        message = root.findViewById(R.id.message);
    }

    @Override
    public void setPlayClickListener(PlayClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
