package com.connectx.controller;

import com.connectx.dto.request.CreateUserRequest;
import com.connectx.dto.request.UpdateUserRequest;
import com.connectx.dto.response.UserResponse;
import com.connectx.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {

        return userService.getUserById(id);
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(
            @PageableDefault(size = 10, sort = "createdAt")
            Pageable pageable) {

        return userService.getAllUsers(pageable);
    }

    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {

        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);
    }
}