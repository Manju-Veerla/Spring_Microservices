package com.example.user.service;

import com.example.user.client.DepartmentClient;
import com.example.user.exceptions.BadRequestException;
import com.example.user.exceptions.DepartmentNotFoundException;
import com.example.user.exceptions.UserNotFoundException;
import com.example.user.model.entities.User;
import com.example.user.model.mapper.UserMapper;
import com.example.user.model.request.DepartmentRequest;
import com.example.user.model.request.UserRequest;
import com.example.user.model.response.UserResponse;
import com.example.user.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private final DepartmentClient departmentClient;

    public UserRequest saveUser(User user) {
        try {
            //TODO verify if dept is valid
            User savedUser = userRepository.save(user);
            return userMapper.toUserRequest(savedUser);
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage(), e);
            throw new BadRequestException("Failed to save user: " + e.getMessage());
        }
    }

    public UserResponse getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        UserRequest userRequest = userMapper.toUserRequest(user);
        ResponseEntity<DepartmentRequest> responseEntity = departmentClient.getDepartmentById(Long.valueOf(user.getDepartmentId()));
        log.info("Response status {} ", responseEntity.getStatusCode());
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            DepartmentRequest departmentRequest = responseEntity.getBody();
            return new UserResponse(departmentRequest, userRequest);

        } else {
            // Handle error case
            throw new DepartmentNotFoundException("Failed to fetch department");
        }

    }

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {

           /* ResponseEntity<DepartmentRequest> responseEntity = restTemplate
                    .getForEntity("http://api-gateway:8080/api/department/" + user.getDepartmentId(),
                            DepartmentRequest.class);*/
            ResponseEntity<DepartmentRequest> responseEntity = departmentClient.getDepartmentById(Long.valueOf(user.getDepartmentId()));
            log.info("Response status {} ", responseEntity.getStatusCode());
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                DepartmentRequest departmentRequest = responseEntity.getBody();
                UserRequest userRequest = userMapper.toUserRequest(user);
                UserResponse userResponse = new UserResponse(departmentRequest, userRequest);
                userResponses.add(userResponse);
            } else {
                // Handle error case
                throw new DepartmentNotFoundException("Failed to fetch department");
            }
        }
        return userResponses;
    }
}
