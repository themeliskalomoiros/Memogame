package sk3m3l1io.duisburg.memogame.utils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;

public final class GoogleUtils {
    private GoogleUtils() {
    }

    public static Player createPlayerFrom(GoogleSignInAccount acc) {
        // TODO: Uncomment after debug
//        if (acc != null) {
//            Player p = new Player(acc.getId(), acc.getDisplayName(), acc.getEmail());
//            if (acc.getPhotoUrl() != null) {
//                p.setPhotoUrl(acc.getPhotoUrl().toString());
//            }
//            return p;
//        }
//
//        return null;

        Player p = new Player("Debug_Player_Id", "Debug Player Name", "debugplayer@gmail.com");
        p.setPhotoUrl("https://images.unsplash.com/photo-1558981852-426c6c22a060?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80");
        return p;
    }
}
