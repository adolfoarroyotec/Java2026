package com.tecmx.ut;

public class User {

    private final String name;
    private final boolean active;
    private final boolean suspended;

    public User(String name, boolean active, boolean suspended) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name is required");
        }

        this.name = name;
        this.active = active;
        this.suspended = suspended;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public boolean canBorrow() {
        return active && !suspended;
    }
}