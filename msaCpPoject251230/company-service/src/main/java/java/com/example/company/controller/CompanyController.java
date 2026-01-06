package java.com.example.company.controller;

import com.example.company.dto.CompanyDetailResponse;
import com.example.company.dto.CompanyListResponse;
import com.example.company.dto.LoginRequest;
import com.example.company.dto.RegisterRequest;
import com.example.company.model.ApprovalYn;
import com.example.company.model.Company;
import com.example.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // =========================
    // 1) 회원가입
    // POST /api/company/register
    // =========================
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Company company = new Company();
            company.setLoginId(request.loginId());
            company.setPw(request.password());
            company.setCompanyNmKo(request.companyNmKo());
            company.setCompanyNmEn(request.companyNmEn());
            company.setBizNo(request.bizNo());
            company.setCeoNm(request.ceoNm());
            company.setManagerNm(request.managerNm());
            company.setManagerPosition(request.managerPosition());
            company.setEmail(request.email());
            company.setPhone(request.phone());
            company.setMobile(request.mobile());
            company.setAddressKo(request.addressKo());
            company.setAddressUs(request.addressUs());
            company.setIndustry(request.industry());
            company.setWebsite(request.website());
            company.setDescription(request.description());

            Company saved = companyService.register(company);

            response.put("success", true);
            response.put("message", "회원가입이 완료되었습니다. 관리자 승인 후 로그인 가능합니다.");
            response.put("data", CompanyDetailResponse.from(saved));
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // =========================
    // 2) 로그인
    // POST /api/company/login
    // =========================
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            var result = companyService.login(request.loginId(), request.password());

            response.put("success", true);
            response.put("message", "로그인 성공");
            response.put("token", result.token());
            response.put("companyName", result.companyName());
            response.put("seqNo", result.seqNo());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    // =========================
    // 3) ID 중복 체크
    // GET /api/company/check-id/{id}
    // =========================
    @GetMapping("/check-id/{id}")
    public ResponseEntity<Map<String, Object>> checkId(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        boolean exists = companyService.checkIdDuplication(id);
        response.put("exists", exists);
        response.put("available", !exists);
        response.put("message", exists ? "이미 사용중인 ID입니다." : "사용 가능한 ID입니다.");

        return ResponseEntity.ok(response);
    }

    // =========================
    // 4) 이메일 중복 체크
    // GET /api/company/check-email/{email}
    // =========================
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();

        boolean exists = companyService.checkEmailDuplication(email);
        response.put("exists", exists);
        response.put("available", !exists);
        response.put("message", exists ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다.");

        return ResponseEntity.ok(response);
    }

    // =========================
    // 5) 사업자등록번호 중복 체크
    // GET /api/company/check-bizno/{bizNo}
    // =========================
    @GetMapping("/check-bizno/{bizNo}")
    public ResponseEntity<Map<String, Object>> checkBizNo(@PathVariable String bizNo) {
        Map<String, Object> response = new HashMap<>();

        boolean exists = companyService.checkBizNoDuplication(bizNo);
        response.put("exists", exists);
        response.put("available", !exists);
        response.put("message", exists ? "이미 등록된 사업자등록번호입니다." : "사용 가능한 사업자등록번호입니다.");

        return ResponseEntity.ok(response);
    }

    // =========================
    // 6) 마이페이지 - 내 정보 조회
    // GET /api/company/mypage/{seqNo}
    // =========================
    @GetMapping("/mypage/{seqNo}")
    public ResponseEntity<Map<String, Object>> getMypage(@PathVariable Long seqNo) {
        Map<String, Object> response = new HashMap<>();

        try {
            Company company = companyService.findById(seqNo)
                    .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

            response.put("success", true);
            response.put("data", CompanyDetailResponse.from(company));
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // =========================
    // 7) 마이페이지 - 내 정보 수정
    // PUT /api/company/mypage/{seqNo}
    // =========================
    @PutMapping("/mypage/{seqNo}")
    public ResponseEntity<Map<String, Object>> updateMypage(
            @PathVariable Long seqNo,
            @RequestBody RegisterRequest request) {

        Map<String, Object> response = new HashMap<>();

        try {
            Company updateData = new Company();
            updateData.setCompanyNmKo(request.companyNmKo());
            updateData.setCompanyNmEn(request.companyNmEn());
            updateData.setCeoNm(request.ceoNm());
            updateData.setManagerNm(request.managerNm());
            updateData.setManagerPosition(request.managerPosition());
            updateData.setEmail(request.email());
            updateData.setPhone(request.phone());
            updateData.setMobile(request.mobile());
            updateData.setAddressKo(request.addressKo());
            updateData.setAddressUs(request.addressUs());
            updateData.setIndustry(request.industry());
            updateData.setWebsite(request.website());
            updateData.setDescription(request.description());

            Company updated = companyService.updateProfile(seqNo, updateData);

            response.put("success", true);
            response.put("message", "정보가 수정되었습니다.");
            response.put("data", CompanyDetailResponse.from(updated));
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // =========================
    // 8) 마이페이지 - 비밀번호 변경
    // PUT /api/company/mypage/{seqNo}/password
    // =========================
    @PutMapping("/mypage/{seqNo}/password")
    public ResponseEntity<Map<String, Object>> changePassword(
            @PathVariable Long seqNo,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            String currentPassword = request.get("currentPassword");
            String newPassword = request.get("newPassword");

            if (currentPassword == null || newPassword == null) {
                throw new IllegalArgumentException("현재 비밀번호와 새 비밀번호를 입력해주세요.");
            }

            companyService.changePassword(seqNo, currentPassword, newPassword);

            response.put("success", true);
            response.put("message", "비밀번호가 변경되었습니다.");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // =========================
    // 9) 마이페이지 - 회원 탈퇴
    // DELETE /api/company/mypage/{seqNo}
    // =========================
    @DeleteMapping("/mypage/{seqNo}")
    public ResponseEntity<Map<String, Object>> withdraw(
            @PathVariable Long seqNo,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            String password = request.get("password");

            if (password == null) {
                throw new IllegalArgumentException("비밀번호를 입력해주세요.");
            }

            companyService.withdraw(seqNo, password);

            response.put("success", true);
            response.put("message", "회원 탈퇴가 완료되었습니다.");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ==================== Admin API ====================

    // =========================
    // 10) Admin - 기업 목록
    // GET /api/company/admin/list
    // =========================
    @GetMapping("/admin/list")
    public ResponseEntity<List<CompanyListResponse>> adminList(
            @RequestParam(value = "keyword", required = false) String keyword) {

        List<Company> companies = (keyword == null || keyword.isBlank())
                ? companyService.findAll()
                : companyService.search(keyword);

        List<CompanyListResponse> result = companies.stream()
                .map(CompanyListResponse::from)
                .toList();

        return ResponseEntity.ok(result);
    }

    // =========================
    // 11) Admin - 기업 상세
    // GET /api/company/admin/{seqNo}
    // =========================
    @GetMapping("/admin/{seqNo}")
    public ResponseEntity<CompanyDetailResponse> adminDetail(@PathVariable Long seqNo) {
        Company company = companyService.findById(seqNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return ResponseEntity.ok(CompanyDetailResponse.from(company));
    }

    // =========================
    // 12) Admin - 승인 처리
    // PUT /api/company/admin/{seqNo}/approval
    // =========================
    @PutMapping("/admin/{seqNo}/approval")
    public ResponseEntity<Map<String, Object>> updateApproval(
            @PathVariable Long seqNo,
            @RequestBody Map<String, String> request) {

        Map<String, Object> response = new HashMap<>();

        try {
            String approvalYn = request.get("approvalYn");
            if (!"Y".equalsIgnoreCase(approvalYn) && !"N".equalsIgnoreCase(approvalYn)) {
                throw new IllegalArgumentException("approvalYn은 Y 또는 N이어야 합니다.");
            }

            companyService.updateApproval(seqNo, ApprovalYn.valueOf(approvalYn.toUpperCase()));

            response.put("success", true);
            response.put("message", "승인 상태가 변경되었습니다.");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // =========================
    // 13) Admin - 월별 가입 통계
    // GET /api/company/admin/stats/join-monthly
    // =========================
    @GetMapping("/admin/stats/join-monthly")
    public ResponseEntity<Map<String, Object>> joinMonthly(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        return ResponseEntity.ok(companyService.getJoinStats(from, to));
    }
}
