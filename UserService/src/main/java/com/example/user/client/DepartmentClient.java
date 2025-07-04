package com.example.user.client;


import com.example.user.model.response.DepartmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface DepartmentClient {

    Logger log = LoggerFactory.getLogger(DepartmentClient.class);

    @GetExchange("api/department/{departmentId}")
    ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long departmentId);


}
