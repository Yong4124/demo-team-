package com.example.company.dto;

public record RegisterRequest(
    String loginId,
    String password,
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
    String description
) {}
