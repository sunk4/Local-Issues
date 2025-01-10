package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerDto;
import com.roman.localIssues.complaint_service.image.ImageDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ComplaintDto(
        UUID id,
        @NotBlank(message = "City name is required") String cityName,
        @NotBlank(message = "Street name is required") String streetName,
        @NotBlank(message = "Description is required") String description,
        @NotBlank(message = "Category is required") String category,
        @NotBlank(message = "Subcategory is required") String subcategory,
        @NotBlank(message = "Type is required") String type,
        List<ImageDto> images,
        String videoUrl,
        UUID claimerId,
        ClaimerDto claimer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}