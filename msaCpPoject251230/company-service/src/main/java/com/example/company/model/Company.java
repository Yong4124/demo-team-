package com.example.company.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "COMPANY") // DB 테이블명에 맞게 수정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO_M200") // DB 컬럼명에 맞게 수정
    private Long seqNoM200;

    @Column(name = "LOGIN_ID", nullable = false, unique = true)
    private String loginId;

    @Column(name = "PW", nullable = false)
    private String pw;

    @Column(name = "BIZ_NO")
    private String bizNo;

    @Column(name = "COMPANY_NM_KO")
    private String companyNmKo;

    @Column(name = "COMPANY_NM_EN")
    private String companyNmEn;

    @Column(name = "CEO_NM")
    private String ceoNm;

    @Column(name = "MANAGER_NM")
    private String managerNm;

    @Column(name = "MANAGER_POSITION")
    private String managerPosition;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "ADDRESS_KO")
    private String addressKo;

    @Column(name = "ADDRESS_US")
    private String addressUs;

    @Column(name = "INDUSTRY")
    private String industry;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPROVAL_YN", nullable = false)
    private ApprovalYn approvalYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEL_YN", nullable = false)
    private DelYn delYn;

    @Column(name = "INSERT_DATE")
    private LocalDate insertDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    private String logoFile;
}
