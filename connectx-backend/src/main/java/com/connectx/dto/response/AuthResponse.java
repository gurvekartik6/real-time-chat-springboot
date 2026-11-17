package com.connectx.dto.response;

public record AuthResponse(

        String accessToken,

        String tokenType,

        long expiresIn,

        UserResponse user

) {}