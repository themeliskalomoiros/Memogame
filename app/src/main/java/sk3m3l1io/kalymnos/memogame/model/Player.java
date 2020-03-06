package sk3m3l1io.kalymnos.memogame.model;

import androidx.annotation.Nullable;

public final class Player {
    private final String name;
    private final String email;
    private final Score score;

    public Player(String name, String email, Score score) {
        this.name = name;
        this.email = email;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Score getScore() {
        return score;
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
