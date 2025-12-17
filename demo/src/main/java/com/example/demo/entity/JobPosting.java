package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_posting")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_member_id", nullable = false)
    private CompanyMember companyMember;

    @Column(nullable = false, length = 500)
    private String title;  // 공고명

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "job_form", length = 500)
    private String jobForm;  // 직업유형

    @Column(name = "job_type", length = 500)
    private String jobType;  // 고용형태

    @Column(name = "job_category", length = 500)
    private String jobCategory;  // 직종

    @Column(length = 500)
    private String industry;  // 업계

    @Column(name = "work_time", length = 500)
    private String workTime;  // 근무시간

    @Column(name = "role_level", length = 500)
    private String roleLevel;  // 직급

    @Column(name = "base_salary", length = 500)
    private String baseSalary;  // 기본급

    @Column(length = 2000)
    private String experience;  // 경력

    @Column(name = "job_location", length = 500)
    private String jobLocation;  // 근무처

    @Column(name = "company_intro", columnDefinition = "TEXT")
    private String companyIntro;  // 회사소개

    @Column(name = "position_summary", columnDefinition = "TEXT")
    private String positionSummary;  // 담당업무

    @Column(name = "skill_qualification", columnDefinition = "TEXT")
    private String skillQualification;  // 기술 및 자격요건

    @Column(columnDefinition = "TEXT")
    private String benefits;  // 복지 및 보상

    @Column(columnDefinition = "TEXT")
    private String notes;  // 유의사항

    @Column(name = "company_type", length = 500)
    private String companyType;  // 기업구분

    @Column(name = "established_date", length = 500)
    private String establishedDate;  // 설립일

    @Column(name = "employee_num", length = 500)
    private String employeeNum;  // 사원수

    @Column(length = 500)
    private String capital;  // 자본금

    @Column(length = 500)
    private String revenue;  // 매출액

    @Column(length = 500)
    private String homepage;

    @Column(name = "posting_yn", nullable = false, length = 1)
    @Builder.Default
    private String postingYn = "2";  // 1: 공고완료, 2: 임시저장

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";

    // 연관관계
    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FavoriteJob> favoriteJobs = new ArrayList<>();
}
