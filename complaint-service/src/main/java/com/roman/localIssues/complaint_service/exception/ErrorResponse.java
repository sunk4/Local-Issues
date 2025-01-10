package com.roman.localIssues.complaint_service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder

public class ErrorResponse {
    private final String message;
    private final int code;
    private final LocalDateTime timestamp;
    private final List<String> details;
}
