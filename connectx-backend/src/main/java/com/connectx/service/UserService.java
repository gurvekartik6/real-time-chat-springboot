package com.connectx.service;

import com.connectx.dto.request.CreateUserRequest;
import com.connectx.dto.request.UpdateUserRequest;
import com.connectx.dto.response.UserResponse;
import com.connectx.entity.UserEntity;
import com.connectx.exception.EmailAlreadyExistsException;
import com.connectx.exception.UserNotFoundException;
import com.connectx.exception.UsernameAlreadyExistsException;
import com.connectx.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists"
            );
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(
                    "Username already exists"
            );
        }

        UserEntity user = new UserEntity();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setDisplayName(request.displayName());

        UserEntity savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return toResponse(user);
    }

    public UserResponse updateUser(
            UUID id,
            UpdateUserRequest request) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                                new UserNotFoundException("User not found"));

        user.setDisplayName(request.displayName());

        return toResponse(userRepository.save(user));
    }

    public void deleteUser(UUID id) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        ));

        userRepository.delete(user);
    }

    private UserResponse toResponse(UserEntity user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt()
        );
    }
}