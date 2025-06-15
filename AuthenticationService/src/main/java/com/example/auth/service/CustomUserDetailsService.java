package com.example.auth.service;

import com.example.auth.client.UserServiceClient;
import com.example.auth.exceptions.UserNotFoundException;
import com.example.auth.model.request.LoginRequest;
import com.example.auth.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("Loading user by username: {}", username);
            UserResponse user = userServiceClient.getUserByUsername(username);
            
            // For security, we'll use the validateCredentials endpoint to verify the password
            // The actual password won't be exposed in the UserResponse
            return new User(
                    user.username(),
                    "", // Password will be validated separately
                    true, true, true, true,
                    getAuthorities()
            );
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

    private List<GrantedAuthority> getAuthorities() {
        //TODO For now, return a default role. You can modify this later when roles are implemented
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public boolean validateCredentials(String username, String password) {
        try {
            // Call the user service to validate credentials
            log.info("Validating credentials for user: {}", username);
            return userServiceClient.validateCredentials(
                new LoginRequest(username, password)
            );
        } catch (Exception e) {
            log.error("Error validating credentials for user: {}", username, e);
            return false;
        }
    }
}