package java.com.example.company.dto;

import com.example.company.model.Company;

import java.time.LocalDate;

public record CompanyListResponse(
    Long no,
    String companyNmKo,
    String companyNmEn,
    String loginId,
    String managerNm,
    String email,
    String phone,
    LocalDate insertDate,
    String approvalYn
) {
    public static CompanyListResponse from(Company c) {
        return new CompanyListResponse(
            c.getSeqNoM200(),
            c.getCompanyNmKo(),
            c.getCompanyNmEn(),
            c.getLoginId(),
            c.getManagerNm(),
            c.getEmail(),
            c.getPhone(),
            c.getInsertDate(),
            c.getApprovalYn() != null ? c.getApprovalYn().name() : "N"
        );
    }
}
