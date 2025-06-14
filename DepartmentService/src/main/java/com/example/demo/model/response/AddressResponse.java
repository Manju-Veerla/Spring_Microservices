package com.example.demo.model.response;

import com.example.demo.model.entities.AddressType;

public record AddressResponse(
		AddressType type,
		String street,
		String city,
		String pincode
) {}
