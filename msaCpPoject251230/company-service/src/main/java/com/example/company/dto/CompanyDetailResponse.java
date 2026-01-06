package com.example.company.dto;

import com.example.company.model.Company;

import java.time.LocalDate;

public record CompanyDetailResponse(
    Long seqNo,
    String loginId,
    String companyNmKo,
    String companyNmEn,
    String bizNo,
    String ceoNm,
    String managerNm,
    String managerPosition,
    String email,
    String phone,
    String mobile,
    String addressKo,
    String addressUs,
    String industry,
    String website,
    String logoFile,
    String description,
    LocalDate insertDate,
    String approvalYn,
    LocalDate updateDate
) {
    public static CompanyDetailResponse from(Company c) {
        return new CompanyDetailResponse(
            c.getSeqNoM200(),
            c.getLoginId(),
            c.getCompanyNmKo(),
            c.getCompanyNmEn(),
            c.getBizNo(),
            c.getCeoNm(),
            c.getManagerNm(),
            c.getManagerPosition(),
            c.getEmail(),
            c.getPhone(),
            c.getMobile(),
            c.getAddressKo(),
            c.getAddressUs(),
            c.getIndustry(),
            c.getWebsite(),
            c.getLogoFile(),
            c.getDescription(),
            c.getInsertDate(),
            c.getApprovalYn() != null ? c.getApprovalYn().name() : "N",
            c.getUpdateDate()
        );
    }
}
