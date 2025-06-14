package com.example.user.service;

import com.example.user.client.DepartmentClient;
import com.example.user.exceptions.BadRequestException;
import com.example.user.exceptions.DepartmentNotFoundException;
import com.example.user.exceptions.UserNotFoundException;
import com.example.user.model.entities.User;
import com.example.user.model.mapper.UserMapper;
import com.example.user.model.request.UserRequest;
import com.example.user.model.response.DepartmentResponse;
import com.example.user.model.response.UserDeptResponse;
import com.example.user.model.response.UserResponse;
import com.example.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private final DepartmentClient departmentClient;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDeptResponse registerUser(UserRequest userRequest) {
        try {
            DepartmentResponse departmentResponse = getDepartmentFromClient(userRequest.departmentId());
            if (departmentResponse != null) {
                log.info("Department found with id: {}", userRequest.departmentId());
                User userToSave = userMapper.userRequestToUser(userRequest);
                // Encode the password before saving
                String encodedPassword = passwordEncoder.encode(userToSave.getPassword());
                userToSave.setPassword(encodedPassword);
                User savedUser = userRepository.save(userToSave);
                return userMapper.toUserResponseWithDepartment(savedUser, departmentResponse);
            } else {
                throw new DepartmentNotFoundException("Department not found with id: " + userRequest.departmentId());
            }
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage(), e);
            throw new BadRequestException("Failed to save user: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public UserDeptResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        DepartmentResponse departmentResponse = getDepartmentFromClient(user.getDepartmentId());
        return userMapper.toUserResponseWithDepartment(user, departmentResponse);
    }

    private DepartmentResponse getDepartmentFromClient(Long  deptId) {

        ResponseEntity<DepartmentResponse> responseEntity = departmentClient.getDepartmentById(deptId);
        log.info("Response status {} ", responseEntity.getStatusCode());
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        } else {
            throw new DepartmentNotFoundException("Failed to fetch department with id: " + deptId);
        }
    }

    @Transactional(readOnly = true)
    public List<UserDeptResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDeptResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            ResponseEntity<DepartmentResponse> responseEntity = departmentClient.getDepartmentById(user.getDepartmentId());
            log.info("Response status {} ", responseEntity.getStatusCode());
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                DepartmentResponse departmentResponse = responseEntity.getBody();
                log.info("Department found with id: {}", user.getDepartmentId());
                UserDeptResponse userResponse = new UserDeptResponse(departmentResponse, user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
                userResponses.add(userResponse);
            } else {
                throw new DepartmentNotFoundException("Failed to fetch department");
            }
        }
        return userResponses;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByDepartmentId(Long departmentId) {
        ResponseEntity<DepartmentResponse> responseEntity = departmentClient.getDepartmentById(departmentId);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            log.info("Department found with id: {}", departmentId);
        } else {
            throw new DepartmentNotFoundException("Failed to fetch department with id: " + departmentId);
        }
    List<User> users = userRepository.findByDepartmentId(departmentId);
    if (users.isEmpty()) {
        throw new UserNotFoundException("No users found for department with id: " + departmentId);
    }
    List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(userMapper.userToUserResponseWithoutPassword(user));
        }
        return userResponses;
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.userToUserResponse(user);
    }

    @Transactional(readOnly = true)
    public boolean validateCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}
