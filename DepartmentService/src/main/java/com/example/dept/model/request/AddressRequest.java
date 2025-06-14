package com.example.dept.model.request;

import com.example.dept.model.entities.AddressType;

public record AddressRequest(
		int id,
		AddressType type,
		String street,
		String city,
		String pincode
) {}
