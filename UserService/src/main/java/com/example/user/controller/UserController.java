package com.example.user.controller;

import com.example.user.model.request.UserRequest;
import com.example.user.model.response.UserResponse;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

	private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest){
        UserResponse savedUser = userService.registerUser(userRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long userId){
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getUsers(){
        List<UserResponse> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
}
