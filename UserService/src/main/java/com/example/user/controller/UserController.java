package com.example.user.controller;

import com.example.user.model.request.UserRequest;
import com.example.user.model.response.UserDeptResponse;
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
    public ResponseEntity<UserDeptResponse> registerUser(@RequestBody UserRequest userRequest){
        UserDeptResponse savedUser = userService.registerUser(userRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDeptResponse> getUser(@PathVariable("id") Long userId){
        UserDeptResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDeptResponse>> getUsers(){
        List<UserDeptResponse> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<UserResponse>> getUsersByDepartmentId(@PathVariable("departmentId") Long departmentId){
        List<UserResponse> users = userService.getUsersByDepartmentId(departmentId);
        return ResponseEntity.ok(users);
    }
}
