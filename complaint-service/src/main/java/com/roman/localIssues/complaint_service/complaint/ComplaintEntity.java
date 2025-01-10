package com.roman.localIssues.complaint_service.complaint;

import com.roman.localIssues.complaint_service.claimer.ClaimerEntity;
import com.roman.localIssues.complaint_service.enums.Status;
import com.roman.localIssues.complaint_service.image.ImageEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "complaint_service_complaint")
public class ComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String cityName;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subcategory;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImageEntity> images;

    private String videoUrl;

    @OneToOne
    @JoinColumn(name = "claimer_id", nullable = false)
    private ClaimerEntity claimer;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

}
