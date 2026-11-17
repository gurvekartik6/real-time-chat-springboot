
package com.connectx.service;

import com.connectx.dto.request.CreateUserRequest;
import com.connectx.dto.request.LoginRequest;
import com.connectx.dto.response.AuthResponse;
import com.connectx.dto.response.UserResponse;
import com.connectx.security.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final long expirationMs;

    public AuthService(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.expirationMs = expirationMs;
    }

    // Register a new user and issue an access token.
    public AuthResponse register(CreateUserRequest request) {

        // Reuse existing user creation and password hashing logic.
        UserResponse user = userService.createUser(request);

        // Generate JWT using the registered user's email.
        String token = jwtService.generateToken(user.email());

        return new AuthResponse(
                token,
                "Bearer",
                expirationMs / 1000,
                user
        );
    }

    // Authenticate an existing user and issue an access token.
    public AuthResponse login(LoginRequest request) {

        // Spring Security verifies the email and password.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // Retrieve the user's public information.
        UserResponse user =
                userService.getUserByEmail(request.email());

        // Generate JWT after successful authentication.
        String token = jwtService.generateToken(user.email());

        return new AuthResponse(
                token,
                "Bearer",
                expirationMs / 1000,
                user
        );
    }
}
