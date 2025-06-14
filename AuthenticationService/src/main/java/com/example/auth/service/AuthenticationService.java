package com.example.auth.service;

import com.example.auth.exceptions.InvalidPasswordException;
import com.example.auth.model.request.LoginRequest;
import com.example.auth.model.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public TokenResponse authenticate(LoginRequest loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.username(),
                    loginRequest.password()
                )
            );

            log.info("User authenticated with given username: {}", loginRequest.username());
            return jwtService.generateToken(loginRequest.username());

        } catch (UsernameNotFoundException e) {
            log.error("User not found: {}", loginRequest.username());
            throw new InvalidPasswordException("Invalid username or password");
        } catch (Exception e) {
            log.error("Error during authentication for user: {}", loginRequest.username(), e);
            throw new InvalidPasswordException("Authentication failed");
        }
    }
}

