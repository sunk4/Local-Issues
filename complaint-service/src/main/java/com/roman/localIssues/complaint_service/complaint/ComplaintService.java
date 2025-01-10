package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;
import com.roman.localIssues.complaint_service.common.PageResponse;
import com.roman.localIssues.complaint_service.enums.Status;

import java.util.UUID;

public interface ComplaintService {
    ComplaintEntity create (ComplaintDto complaintDto, ClaimerEntity claimerEntity);

    ComplaintDto getComplaint (UUID id);

    void updateStatus (UUID id, PatchComplaintDto patchComplaintDto);

    PageResponse<ComplaintDto> getAllComplaintPaginated (
            int page, int size,
            String cityName,
            String streetName,
            String category,
            String subcategory,
            String type,
            Status status
    );
}
