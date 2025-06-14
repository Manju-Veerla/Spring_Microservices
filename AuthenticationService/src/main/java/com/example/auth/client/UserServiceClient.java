package com.example.auth.client;

import com.example.auth.model.request.LoginRequest;
import com.example.auth.model.response.UserResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


public interface UserServiceClient {

    @GetMapping("/username/{username}")
    UserResponse getUserByUsername(@PathVariable String username);

    @PostMapping("/validate")
    boolean validateCredentials(@RequestBody LoginRequest request);

}
