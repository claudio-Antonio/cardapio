package com.example.cardapio.domain.enums;

public enum UserIfoodRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserIfoodRole(String role) {
        this.role = role;
    }

    String getRole() {
        return role;
    }
}
