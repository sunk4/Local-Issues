package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<ComplaintEntity, UUID> {

    @Query("SELECT c FROM ComplaintEntity c " +
            "WHERE (:cityName IS NULL OR c.cityName LIKE %:cityName%) " +
            "AND (:streetName IS NULL OR c.streetName = :streetName) " +
            "AND (:category IS NULL OR c.category = :category) " +
            "AND (:subcategory IS NULL OR c.subcategory = :subcategory) " +
            "AND (:type IS NULL OR c.type = :type) " +
            "AND (:status IS NULL OR c.status = :status)")
    Page<ComplaintEntity> findComplaintsWithFilters(
            @Param("cityName") String cityName,
            @Param("streetName") String streetName,
            @Param("category") String category,
            @Param("subcategory") String subcategory,
            @Param("type") String type,
            @Param("status") Status status,
            Pageable pageable);
}
