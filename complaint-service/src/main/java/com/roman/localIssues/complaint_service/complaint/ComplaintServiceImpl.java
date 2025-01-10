package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;

    @Override
    public ComplaintEntity create (ComplaintDto complaintDto, ClaimerEntity claimerEntity) {
        ComplaintEntity complaintEntity = complaintMapper.toEntity(complaintDto);
        complaintEntity.setClaimer(claimerEntity);

        return complaintRepository.save(complaintEntity);
    }

    @Override
    public ComplaintDto getComplaint (UUID id) {
        ComplaintEntity complaintEntity =
                complaintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                        "Complaint not found"));

        return complaintMapper.toDto(complaintEntity);

    }
}
