package com.betkowski.incidentmanager.security;

public record LoginRequest(
        String username,
        String password
) {
}
