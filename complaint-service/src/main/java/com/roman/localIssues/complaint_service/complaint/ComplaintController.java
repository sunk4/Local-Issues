package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerDto;
import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;
import com.roman.localIssues.complaint_service.claimer.ClaimerService;
import com.roman.localIssues.complaint_service.common.PageResponse;
import com.roman.localIssues.complaint_service.enums.Status;
import com.roman.localIssues.complaint_service.image.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<PageResponse<ComplaintDto>> getComplaints (
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String streetName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Status status

    ) {
        PageResponse<ComplaintDto> complaintsDtoPageResponse =
                complaintService.getAllComplaintPaginated(page, size,
                        cityName, streetName, category, subcategory, type
                        , status);
        return ResponseEntity.ok(complaintsDtoPageResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaint (@PathVariable UUID id) {
        return ResponseEntity.ok(complaintService.getComplaint(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStatusOfComplaint (
            @PathVariable UUID id,
            @RequestBody PatchComplaintDto patchComplaintDto
    ) {
        complaintService.updateStatus(id, patchComplaintDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
