package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerDto;
import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;
import com.roman.localIssues.complaint_service.claimer.ClaimerService;
import com.roman.localIssues.complaint_service.image.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final ImageService imageService;
    private final ClaimerService claimerService;

    @PostMapping
    public ResponseEntity<Void> create (
            @Valid @RequestPart("complaintDto") ComplaintDto complaintDto,
            @Valid @RequestPart("claimerDto") ClaimerDto claimerDto,
            @Valid @RequestPart("image") MultipartFile image

    ) {
        ClaimerEntity claimerEntity = claimerService.create(claimerDto);
        ComplaintEntity complaintEntity = complaintService.create(complaintDto, claimerEntity);
        imageService.uploadFile(image, complaintEntity);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaint (@PathVariable UUID id) {
        return ResponseEntity.ok(complaintService.getComplaint(id));
    }

}
