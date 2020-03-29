package sk3m3l1io.kalymnos.memogame.utils;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import sk3m3l1io.kalymnos.memogame.pojos.Player;

public final class GoogleUtils {
    private GoogleUtils(){}

    public static Player createPlayerFrom(GoogleSignInAccount acc){
        if (acc != null) {
            Player p = new Player(acc.getId(), acc.getDisplayName(), acc.getEmail());
            if (acc.getPhotoUrl() != null) {
                p.setPhotoUrl(acc.getPhotoUrl().toString());
            }
            return p;
        }

        return null;
    }
}
