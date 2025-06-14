package com.example.demo.model.response;

import java.util.Set;


public record DepartmentResponse(
        Long id,
        String departmentName,
        Set<AddressResponse> address,
        String departmentCode
) {}
