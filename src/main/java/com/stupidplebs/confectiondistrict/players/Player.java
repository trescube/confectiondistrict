package com.stupidplebs.confectiondistrict.players;

import java.util.Objects;

public class Player {
    private final String name;

    public Player(final String name) {
        if (null == name) {
            throw new NullPointerException("name cannot be null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("name cannot be blank");
        }

        this.name = name.trim();

    }

    public String getName() {
        return name;
    }

    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Player other = (Player) obj;

        return name.equals(other.getName());

    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return "Player{name=" + name + "}";
    }

}
