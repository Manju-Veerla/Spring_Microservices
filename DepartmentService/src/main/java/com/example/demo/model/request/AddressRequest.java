package com.example.demo.model.request;

import com.example.demo.model.entities.AddressType;

public record AddressRequest(
		int id,
		AddressType type,
		String street,
		String city,
		String pincode
) {}
