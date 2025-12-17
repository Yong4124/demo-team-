package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_member")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, length = 100, unique = true)
    private String loginId;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(name = "manager_name", nullable = false, length = 500)
    private String managerName;  // 담당자명

    @Column(length = 500)
    private String department;  // 부서명

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "business_regist_num", nullable = false, length = 500)
    private String businessRegistNum;  // 사업자번호

    @Column(nullable = false, length = 500)
    private String company;  // 회사명

    @Column(name = "president_name", nullable = false, length = 500)
    private String presidentName;  // 대표자명

    @Column(name = "company_address", columnDefinition = "TEXT")
    private String companyAddress;  // 회사주소

    @Column(name = "parent_company_cd", length = 100)
    private String parentCompanyCd;  // 그룹사 코드

    @Column(name = "insert_date")
    private LocalDate insertDate;

    @Column(name = "approval_yn", nullable = false, length = 1)
    @Builder.Default
    private String approvalYn = "N";

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";

    // 연관관계
    @OneToMany(mappedBy = "companyMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<JobPosting> jobPostings = new ArrayList<>();

    @OneToMany(mappedBy = "companyMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CompanyLogo> logos = new ArrayList<>();

    @OneToMany(mappedBy = "companyMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CompanyImage> images = new ArrayList<>();
}
