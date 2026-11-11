package com.connectx.dto.response;

public record UserResponse(
        int id,
        String username,
        String email,
        String displayName,
        String createdAt
) {}