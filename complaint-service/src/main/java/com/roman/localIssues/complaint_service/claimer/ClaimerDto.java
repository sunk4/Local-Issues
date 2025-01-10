package com.roman.localIssues.complaint_service.claimer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClaimerDto(
        UUID id,
        @NotBlank(message = "First name is required") String firstName,
        String lastName,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
