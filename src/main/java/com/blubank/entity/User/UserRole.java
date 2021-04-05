package com.blubank.entity.User;

public enum UserRole {
    ADMIN, MODERATOR, USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
