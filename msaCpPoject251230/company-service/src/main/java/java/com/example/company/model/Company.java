package java.com.example.company.model;

import com.example.company.model.ApprovalYn;
import com.example.company.model.DelYn;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 기업 회원 테이블 (T_JB_M200)
 * 원본 사이트의 기업 회원 정보를 관리
 */
@Entity
@Table(name = "T_JB_M200")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO_M200")
    private Long seqNoM200;

    // 로그인 ID
    @Column(name = "ID", nullable = false, length = 100, unique = true)
    private String loginId;

    // 비밀번호 (해시)
    @Column(name = "PW", nullable = false, length = 500)
    private String pw;

    // 회사명 (한글)
    @Column(name = "COMPANY_NM_KO", nullable = false, length = 500)
    private String companyNmKo;

    // 회사명 (영문)
    @Column(name = "COMPANY_NM_EN", length = 500)
    private String companyNmEn;

    // 사업자등록번호
    @Column(name = "BIZ_NO", length = 100)
    private String bizNo;

    // 대표자명
    @Column(name = "CEO_NM", length = 200)
    private String ceoNm;

    // 담당자명
    @Column(name = "MANAGER_NM", nullable = false, length = 200)
    private String managerNm;

    // 담당자 직책
    @Column(name = "MANAGER_POSITION", length = 100)
    private String managerPosition;

    // 담당자 이메일
    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    // 담당자 전화번호
    @Column(name = "PHONE", nullable = false, length = 100)
    private String phone;

    // 담당자 휴대폰번호
    @Column(name = "MOBILE", length = 100)
    private String mobile;

    // 회사 주소 (한국)
    @Column(name = "ADDRESS_KO", length = 1000)
    private String addressKo;

    // 회사 주소 (미국)
    @Column(name = "ADDRESS_US", length = 1000)
    private String addressUs;

    // 업종
    @Column(name = "INDUSTRY", length = 500)
    private String industry;

    // 회사 웹사이트
    @Column(name = "WEBSITE", length = 500)
    private String website;

    // 회사 로고 파일명
    @Column(name = "LOGO_FILE", length = 500)
    private String logoFile;

    // 회사 소개
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    // 가입일
    @Column(name = "INSERT_DATE")
    private LocalDate insertDate;

    // 승인여부 Y/N
    @Column(name = "APPROVAL_YN", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private ApprovalYn approvalYn = ApprovalYn.N;

    // 삭제여부 Y/N
    @Column(name = "DEL_YN", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private com.example.company.model.DelYn delYn = com.example.company.model.DelYn.N;

    // 최종 수정일
    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @PrePersist
    public void prePersist() {
        if (this.insertDate == null) {
            this.insertDate = LocalDate.now();
        }
        if (this.approvalYn == null) {
            this.approvalYn = ApprovalYn.N;
        }
        if (this.delYn == null) {
            this.delYn = DelYn.N;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDate.now();
    }
}
