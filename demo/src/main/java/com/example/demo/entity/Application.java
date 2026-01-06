package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_member_id", nullable = false)
    private PersonalMember personalMember;

    @Column(name = "review_status")
    private Integer reviewStatus;  // 0: 미심사, 1: 1차 합격, 2: 합격, 3: 불합격
    
    @Column(length = 50)
    private String status;  // 접수완료, 서류검토중, 서류합격, 면접예정, 최종합격, 불합격

    @Column(name = "cancel_status", nullable = false, length = 1)
    @Builder.Default
    private String cancelStatus = "N";  // Y: 지원취소, N: 지원유지

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "접수완료";
        }
    }
}
