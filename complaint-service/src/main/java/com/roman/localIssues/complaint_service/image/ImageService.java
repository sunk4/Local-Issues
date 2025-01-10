package com.roman.localIssues.complaint_service.image;

import com.roman.localIssues.complaint_service.complaint.ComplaintEntity;
import org.springframework.web.multipart.MultipartFile;



public interface ImageService {
    void uploadFile (MultipartFile file,  ComplaintEntity complaintEntity);

}
