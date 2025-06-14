package com.example.user.model.response;

public record UserDeptResponse(
		DepartmentResponse department,
		String username, String firstName, String lastName, String email
) {}