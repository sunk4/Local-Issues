package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;
import com.roman.localIssues.complaint_service.common.PageResponse;
import com.roman.localIssues.complaint_service.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;

    @Override
    public ComplaintEntity create (ComplaintDto complaintDto, ClaimerEntity claimerEntity) {
        ComplaintEntity complaintEntity = complaintMapper.toEntity(complaintDto);
        complaintEntity.setStatus(Status.New);
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

    @Override
    public void updateStatus (UUID id, PatchComplaintDto patchComplaintDto) {
        ComplaintEntity complaintEntity =
                complaintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                        "Complaint not found"));

        complaintEntity.setStatus(patchComplaintDto.status());

        complaintRepository.save(complaintEntity);

    }

    @Override
    public PageResponse<ComplaintDto> getAllComplaintPaginated (int page,
                                                                int size,String cityName,
                                                                String streetName,
                                                                String category,
                                                                String subcategory,
                                                                String type,
                                                                Status status) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(
                "createdAt").descending());

        Page<ComplaintEntity> complaintEntities = complaintRepository.findComplaintsWithFilters(
                cityName, streetName, category, subcategory, type, status, pageRequest);

        List<ComplaintDto> complaintDtos =
                complaintMapper.entityListToDto(complaintEntities.getContent());

        return new PageResponse<>(
                complaintDtos,
                complaintEntities.getNumber(),
                complaintEntities.getSize(),
                complaintEntities.getTotalElements(),
                complaintEntities.getTotalPages()
        );

    }
}
