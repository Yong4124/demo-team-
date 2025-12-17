package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resume_career")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(length = 500)
    private String company;  // 회사명

    @Column(length = 500)
    private String department;  // 부서명

    @Column(name = "join_date", length = 100)
    private String joinDate;  // 입사년월

    @Column(name = "retire_date", length = 100)
    private String retireDate;  // 퇴사년월

    @Column(length = 500)
    private String position;  // 직급/직책

    @Column(length = 500)
    private String salary;  // 연봉

    @Column(name = "position_summary", columnDefinition = "TEXT")
    private String positionSummary;  // 담당업무

    @Column(columnDefinition = "TEXT")
    private String experience;  // 경력기술서

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
}
