package com.example.user.model.response;

import com.example.user.model.enums.Role;

public record UserResponse(
		String username, String firstName, String lastName, String email, Role role
) {}