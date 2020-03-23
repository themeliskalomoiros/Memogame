package sk3m3l1io.kalymnos.memogame.view;

import android.view.View;

public interface ViewMvc {
    /*
     * Get the root Android View which is used internally by this MVC View for
     * presenting data to the user.
     * The returned Android View might be used by an MVC Controller in order to query
     * or alter the properties of either the root Android View itself, or any of its
     * child Android View's.
     *
     * From https://www.techyourchance.com/mvp-mvc-android-1/
     */
    View getRootView();
}
