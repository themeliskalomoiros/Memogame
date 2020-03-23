package sk3m3l1io.kalymnos.memogame.pojos;

import androidx.annotation.Nullable;

public final class Player {
    private final String name;
    private final String email;

    public Player(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) return true;
        if (!(o instanceof Player)) return false;
        Player other = (Player) o;
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode() * 1579;
    }
}
