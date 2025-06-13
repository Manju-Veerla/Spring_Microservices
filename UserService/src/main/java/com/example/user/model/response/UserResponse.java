package com.example.user.model.response;

import com.example.user.model.request.DepartmentRequest;
import com.example.user.model.request.UserRequest;

public record UserResponse(
		DepartmentRequest department,
		UserRequest userRequest
) {}