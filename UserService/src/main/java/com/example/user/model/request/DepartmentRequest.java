package com.example.user.model.request;

import java.util.Set;

public record DepartmentRequest(
		Long id,
		String departmentName,
		Set<AddressRequest> address,
		String departmentCode
) {}