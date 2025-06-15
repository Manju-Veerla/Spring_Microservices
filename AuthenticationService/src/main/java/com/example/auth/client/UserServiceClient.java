package com.example.auth.client;

import com.example.auth.model.request.LoginRequest;
import com.example.auth.model.response.UserResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/api/users")
public interface UserServiceClient {

    @GetExchange("/username/{username}")
    UserResponse getUserByUsername(@PathVariable String username);

    @PostExchange("/validate")
    boolean validateCredentials(@RequestBody LoginRequest request);
}
