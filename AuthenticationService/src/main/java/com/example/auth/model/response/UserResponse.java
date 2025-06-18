package com.example.auth.model.response;

import com.example.auth.model.enums.Role;

public record UserResponse(
		String username, String firstName, String lastName, String email, Role role
) {}