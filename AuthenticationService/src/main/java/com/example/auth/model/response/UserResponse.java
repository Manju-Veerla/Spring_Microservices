package com.example.auth.model.response;

public record UserResponse(
		String username, String firstName, String lastName, String email
) {}