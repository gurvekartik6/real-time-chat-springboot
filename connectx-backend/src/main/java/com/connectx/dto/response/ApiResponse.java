package com.connectx.dto.response;

import java.time.Instant;
import java.util.Map;

public record ApiResponse(
        boolean success,
        int status,
        String message,
        String path,
        Instant timestamp,
        Map<String, String> errors
) {}