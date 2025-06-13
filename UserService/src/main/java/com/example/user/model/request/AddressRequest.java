package com.example.user.model.request;

import com.example.user.model.enums.AddressType;

public record AddressRequest(
		int id,
		AddressType type,
		String street,
		String city,
		String pincode
) {}