package com.roman.localIssues.complaint_service.claimer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimerRepository extends JpaRepository<ClaimerEntity, UUID> {
}
