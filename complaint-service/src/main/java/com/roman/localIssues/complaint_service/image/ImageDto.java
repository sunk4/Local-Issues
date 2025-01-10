package com.roman.localIssues.complaint_service.image;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImageDto(
        UUID id,
        String url,
        @NotNull(message = "File is required")
        MultipartFile file,
        UUID complaintId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
