package com.example.user.model.response;

public record UserResponse(
		DepartmentResponse department,
		String username, String firstName, String lastName, String email
) {}