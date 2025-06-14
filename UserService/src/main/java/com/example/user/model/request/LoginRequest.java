package com.example.user.model.request;

public record LoginRequest(
    String username,
    String password
) {
}
