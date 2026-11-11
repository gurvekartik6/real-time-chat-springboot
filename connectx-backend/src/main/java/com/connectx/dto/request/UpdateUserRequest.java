package com.connectx.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "Display name is required")
        @Size(max = 100)
        String displayName

) {}