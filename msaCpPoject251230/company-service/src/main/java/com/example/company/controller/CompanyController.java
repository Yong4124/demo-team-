package com.example.company.controller;

import com.example.company.dto.CompanyDetailResponse;
import com.example.company.dto.CompanyListResponse;
import com.example.company.dto.LoginRequest;
import com.example.company.dto.RegisterRequest;
import com.example.company.model.ApprovalYn;
import com.example.company.model.Company;
import com.example.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompanyController {

    private final CompanyService companyService;

    // ==================== 회원(Company) ====================

    @PostMapping("/company/register")
    public CompanyDetailResponse register(@RequestBody RegisterRequest req) {
        Company saved = companyService.register(toEntity(req));
        return CompanyDetailResponse.from(saved);
    }

    @PostMapping("/company/login")
    public CompanyService.LoginResult login(@RequestBody LoginRequest req) {
        // record 접근: req.loginId(), req.password()
        return companyService.login(req.loginId(), req.password());
    }

    @GetMapping("/company/{seqNo}")
    public CompanyDetailResponse getMyInfo(@PathVariable Long seqNo) {
        Company company = companyService.findById(seqNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        return CompanyDetailResponse.from(company);
    }

    @PutMapping("/company/{seqNo}")
    public CompanyDetailResponse updateProfile(@PathVariable Long seqNo,
                                               @RequestBody RegisterRequest req) {
        Company updateData = toEntityForUpdate(req); // 업데이트용(비번 제외)
        Company updated = companyService.updateProfile(seqNo, updateData);
        return CompanyDetailResponse.from(updated);
    }

    @PutMapping("/company/{seqNo}/password")
    public void changePassword(@PathVariable Long seqNo,
                               @RequestParam String currentPassword,
                               @RequestParam String newPassword) {
        companyService.changePassword(seqNo, currentPassword, newPassword);
    }

    @DeleteMapping("/company/{seqNo}")
    public void withdraw(@PathVariable Long seqNo,
                         @RequestParam String password) {
        companyService.withdraw(seqNo, password);
    }

    // ==================== Admin ====================

    @GetMapping("/admin/companies")
    public List<CompanyListResponse> adminCompanies(@RequestParam(required = false) String keyword) {
        List<Company> list = (keyword == null || keyword.isBlank())
                ? companyService.findAll()
                : companyService.search(keyword);

        return list.stream().map(CompanyListResponse::from).toList();
    }

    @PatchMapping("/admin/companies/{seqNo}/approval")
    public void updateApproval(@PathVariable Long seqNo,
                               @RequestParam ApprovalYn approvalYn) {
        companyService.updateApproval(seqNo, approvalYn);
    }

    @GetMapping("/admin/companies/stats/join")
    public Map<String, Object> joinStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return companyService.getJoinStats(from, to);
    }

    // ==================== 내부 변환 메서드 ====================

    /**
     * 회원가입용 엔티티 변환 (비밀번호 포함)
     */
    private Company toEntity(RegisterRequest req) {
        return Company.builder()
                .loginId(req.loginId())
                .pw(req.password())
                .bizNo(req.bizNo())
                .companyNmKo(req.companyNmKo())
                .companyNmEn(req.companyNmEn())
                .ceoNm(req.ceoNm())
                .managerNm(req.managerNm())
                .managerPosition(req.managerPosition())
                .email(req.email())
                .phone(req.phone())
                .mobile(req.mobile())
                .addressKo(req.addressKo())
                .addressUs(req.addressUs())
                .industry(req.industry())
                .website(req.website())
                .description(req.description())
                .build();
    }

    /**
     * 수정용 엔티티 변환 (비밀번호 제외)
     * - 비밀번호는 changePassword API로만 변경하도록 분리
     */
    private Company toEntityForUpdate(RegisterRequest req) {
        return Company.builder()
                .bizNo(req.bizNo())
                .companyNmKo(req.companyNmKo())
                .companyNmEn(req.companyNmEn())
                .ceoNm(req.ceoNm())
                .managerNm(req.managerNm())
                .managerPosition(req.managerPosition())
                .email(req.email())
                .phone(req.phone())
                .mobile(req.mobile())
                .addressKo(req.addressKo())
                .addressUs(req.addressUs())
                .industry(req.industry())
                .website(req.website())
                .description(req.description())
                .build();
    }
}
