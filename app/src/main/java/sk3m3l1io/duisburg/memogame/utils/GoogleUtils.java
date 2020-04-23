package sk3m3l1io.duisburg.memogame.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import sk3m3l1io.duisburg.memogame.model.pojos.Player;

public final class GoogleUtils {
    private GoogleUtils() {
    }

    public static Player createPlayerFrom(GoogleSignInAccount acc) {
        if (acc != null) {
            Player p = new Player(acc.getId(), acc.getDisplayName(), acc.getEmail());
            if (acc.getPhotoUrl() != null) {
                p.setPhotoUrl(acc.getPhotoUrl().toString());
            }
            return p;
        }

        return null;
    }

    public static GoogleSignInClient getSignInClient(AppCompatActivity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        return GoogleSignIn.getClient(activity, gso);
    }
}
