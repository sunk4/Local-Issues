package com.roman.localIssues.complaint_service.complaint;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<ComplaintEntity, UUID> {
}
