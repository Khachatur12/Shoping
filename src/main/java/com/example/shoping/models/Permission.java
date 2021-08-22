package com.example.shoping.models;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    ADMIN_PERMISSION("admin:permission"),
    USER_PERMISSION("user:permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return this.permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
