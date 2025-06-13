package com.example.user.model.response;

import com.example.user.model.enums.AddressType;

public record AddressResponse(
		int id,
		AddressType type,
		String street,
		String city,
		String pincode
) {}