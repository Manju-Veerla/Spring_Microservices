package com.example.dept.model.response;

import com.example.dept.model.entities.AddressType;

public record AddressResponse(
		AddressType type,
		String street,
		String city,
		String pincode
) {}
