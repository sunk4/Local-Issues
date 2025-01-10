package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;

import java.util.UUID;

public interface ComplaintService {
    ComplaintEntity create (ComplaintDto complaintDto, ClaimerEntity claimerEntity);

    ComplaintDto getComplaint (UUID id);
}
