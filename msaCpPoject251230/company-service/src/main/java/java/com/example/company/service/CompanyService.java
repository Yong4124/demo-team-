package java.com.example.company.service;

import com.example.company.model.Company;
import com.example.company.model.ApprovalYn;
import com.example.company.model.DelYn;
import com.example.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public Company register(Company company) {
        // ID 중복 체크
        if (companyRepository.existsByLoginIdAndDelYn(company.getLoginId(), DelYn.N)) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다.");
        }

        // 이메일 중복 체크
        if (companyRepository.existsByEmailAndDelYn(company.getEmail(), DelYn.N)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        // 사업자등록번호 중복 체크 (있는 경우)
        if (company.getBizNo() != null && !company.getBizNo().isEmpty()) {
            if (companyRepository.existsByBizNoAndDelYn(company.getBizNo(), DelYn.N)) {
                throw new IllegalArgumentException("이미 등록된 사업자등록번호입니다.");
            }
        }

        // 비밀번호 암호화
        company.setPw(passwordEncoder.encode(company.getPw()));
        company.setInsertDate(LocalDate.now());
        company.setApprovalYn(ApprovalYn.N);
        company.setDelYn(DelYn.N);

        return companyRepository.save(company);
    }

    /**
     * 로그인
     */
    public LoginResult login(String loginId, String password) {
        Company company = companyRepository.findByLoginIdAndDelYn(loginId, DelYn.N)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(password, company.getPw())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 승인 여부 체크
        if (company.getApprovalYn() != ApprovalYn.Y) {
            throw new IllegalArgumentException("관리자 승인 대기중입니다. 승인 후 로그인 가능합니다.");
        }

        // 간단한 토큰 생성 (실제 프로젝트에서는 JWT 사용 권장)
        String token = UUID.randomUUID().toString();

        return new LoginResult(token, company.getCompanyNmKo(), company.getSeqNoM200());
    }

    /**
     * ID 중복 체크
     */
    @Transactional(readOnly = true)
    public boolean checkIdDuplication(String loginId) {
        return companyRepository.existsByLoginIdAndDelYn(loginId, DelYn.N);
    }

    /**
     * 이메일 중복 체크
     */
    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        return companyRepository.existsByEmailAndDelYn(email, DelYn.N);
    }

    /**
     * 사업자등록번호 중복 체크
     */
    @Transactional(readOnly = true)
    public boolean checkBizNoDuplication(String bizNo) {
        return companyRepository.existsByBizNoAndDelYn(bizNo, DelYn.N);
    }

    /**
     * 회원 정보 조회 (by loginId)
     */
    @Transactional(readOnly = true)
    public Optional<Company> findByLoginId(String loginId) {
        return companyRepository.findByLoginIdAndDelYn(loginId, DelYn.N);
    }

    /**
     * 회원 정보 조회 (by seqNo)
     */
    @Transactional(readOnly = true)
    public Optional<Company> findById(Long seqNo) {
        return companyRepository.findById(seqNo)
                .filter(c -> c.getDelYn() == DelYn.N);
    }

    /**
     * 회원 정보 수정 (마이페이지)
     */
    public Company updateProfile(Long seqNo, Company updateData) {
        Company company = companyRepository.findById(seqNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 수정 가능한 필드들
        if (updateData.getCompanyNmKo() != null) {
            company.setCompanyNmKo(updateData.getCompanyNmKo());
        }
        if (updateData.getCompanyNmEn() != null) {
            company.setCompanyNmEn(updateData.getCompanyNmEn());
        }
        if (updateData.getCeoNm() != null) {
            company.setCeoNm(updateData.getCeoNm());
        }
        if (updateData.getManagerNm() != null) {
            company.setManagerNm(updateData.getManagerNm());
        }
        if (updateData.getManagerPosition() != null) {
            company.setManagerPosition(updateData.getManagerPosition());
        }
        if (updateData.getEmail() != null) {
            // 이메일 변경 시 중복 체크
            if (!company.getEmail().equals(updateData.getEmail())) {
                if (companyRepository.existsByEmailAndDelYn(updateData.getEmail(), DelYn.N)) {
                    throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
                }
            }
            company.setEmail(updateData.getEmail());
        }
        if (updateData.getPhone() != null) {
            company.setPhone(updateData.getPhone());
        }
        if (updateData.getMobile() != null) {
            company.setMobile(updateData.getMobile());
        }
        if (updateData.getAddressKo() != null) {
            company.setAddressKo(updateData.getAddressKo());
        }
        if (updateData.getAddressUs() != null) {
            company.setAddressUs(updateData.getAddressUs());
        }
        if (updateData.getIndustry() != null) {
            company.setIndustry(updateData.getIndustry());
        }
        if (updateData.getWebsite() != null) {
            company.setWebsite(updateData.getWebsite());
        }
        if (updateData.getDescription() != null) {
            company.setDescription(updateData.getDescription());
        }

        company.setUpdateDate(LocalDate.now());
        return companyRepository.save(company);
    }

    /**
     * 비밀번호 변경
     */
    public void changePassword(Long seqNo, String currentPassword, String newPassword) {
        Company company = companyRepository.findById(seqNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, company.getPw())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        // 새 비밀번호 암호화 후 저장
        company.setPw(passwordEncoder.encode(newPassword));
        company.setUpdateDate(LocalDate.now());
        companyRepository.save(company);
    }

    /**
     * 회원 탈퇴 (Soft Delete)
     */
    public void withdraw(Long seqNo, String password) {
        Company company = companyRepository.findById(seqNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, company.getPw())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        company.setDelYn(DelYn.Y);
        company.setUpdateDate(LocalDate.now());
        companyRepository.save(company);
    }

    // ==================== Admin 기능 ====================

    /**
     * 전체 기업 목록 (Admin)
     */
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepository.findAllByDelYnOrderBySeqNoM200Desc(DelYn.N);
    }

    /**
     * 기업 검색 (Admin)
     */
    @Transactional(readOnly = true)
    public List<Company> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        return companyRepository.searchByKeyword(keyword.trim(), DelYn.N);
    }

    /**
     * 승인 처리 (Admin)
     */
    public void updateApproval(Long seqNo, ApprovalYn approvalYn) {
        Company company = companyRepository.findById(seqNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        company.setApprovalYn(approvalYn);
        company.setUpdateDate(LocalDate.now());
        companyRepository.save(company);
    }

    /**
     * 월별 가입 통계 (Admin)
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getJoinStats(LocalDate from, LocalDate to) {
        List<Object[]> raw = companyRepository.countByInsertDateBetween(from, to);

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();

        for (Object[] row : raw) {
            labels.add((String) row[0]);
            data.add((Long) row[1]);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("data", data);
        return result;
    }

    // ==================== 내부 클래스 ====================

    public record LoginResult(String token, String companyName, Long seqNo) {}
}
