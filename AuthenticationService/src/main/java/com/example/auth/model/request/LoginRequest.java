package com.example.auth.model.request;

public record LoginRequest (
    String username,
    String password
) {
}
