package com.roman.localIssues.complaint_service.claimer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "complaint_service_claimer")
public class ClaimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;


}
