package com.example.user.service;

import com.example.user.client.DepartmentClient;
import com.example.user.model.entities.User;
import com.example.user.model.mapper.UserMapper;
import com.example.user.model.request.UserRequest;
import com.example.user.model.response.DepartmentResponse;
import com.example.user.model.response.UserDeptResponse;
import com.example.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private DepartmentClient departmentClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequest testUserRequest;
    private User testUser;
    private User savedUser;
    private DepartmentResponse departmentResponse;

    @BeforeEach
    void setUp() {
        testUserRequest = new UserRequest(
                1L, "Manju", "testuser", "password123",
                "Veerla", "manju.v@example.com", 1L
        );

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setFirstName("Manju");
        testUser.setLastName("Veerla");
        testUser.setEmail("manju.v@example.com");
        testUser.setDepartmentId(1L);

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setPassword("$2a$10$somehashedpassword"); // Example of hashed password
        savedUser.setFirstName("Manju");
        savedUser.setLastName("Veerla");
        savedUser.setEmail("manju.v@example.com");
        savedUser.setDepartmentId(1L);

        departmentResponse = new DepartmentResponse(1L, "IT", "IT-001");
    }

    @Test
    void registerUser_ShouldEncodePassword() {
        // Arrange
        ResponseEntity<DepartmentResponse> responseEntity = ResponseEntity.ok(departmentResponse);
        when(departmentClient.getDepartmentById(1L)).thenReturn(responseEntity);
        when(userMapper.userRequestToUser(any(UserRequest.class))).thenReturn(testUser);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$somehashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Create expected response
        UserDeptResponse expectedResponse = new UserDeptResponse(
            departmentResponse,
            "testuser",
            "Manju",
            "Veerla",
            "manju.v@example.com"
        );
        
        when(userMapper.toUserResponseWithDepartment(any(User.class), any(DepartmentResponse.class)))
                .thenReturn(expectedResponse);

        // Act
        UserDeptResponse result = userService.registerUser(testUserRequest);

        // Assert
        assertNotNull(result);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        assertEquals("testuser", result.username());
        assertEquals("Manju", result.firstName());
        assertEquals("Veerla", result.lastName());
        assertEquals("manju.v@example.com", result.email());
        assertNotNull(result.department());
        assertEquals("IT", result.department().departmentName());
    }
}
