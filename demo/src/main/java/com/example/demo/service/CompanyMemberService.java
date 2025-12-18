package com.example.demo.service;

import com.example.demo.entity.CompanyMember;
import com.example.demo.repository.CompanyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyMemberService {

    private final CompanyMemberRepository companyMemberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public CompanyMember register(CompanyMember member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setInsertDate(LocalDate.now());
        member.setApprovalYn("Y");  // 테스트용 자동 승인 (나중에 "N"으로 변경)
        member.setDelYn("N");
        return companyMemberRepository.save(member);
    }

    // 아이디 중복 확인
    public boolean existsByLoginId(String loginId) {
        return companyMemberRepository.existsByLoginId(loginId);
    }

    // 이메일 중복 확인
    public boolean existsByEmail(String email) {
        return companyMemberRepository.existsByEmail(email);
    }

    // 사업자번호 중복 확인
    public boolean existsByBusinessRegistNum(String businessRegistNum) {
        return companyMemberRepository.existsByBusinessRegistNum(businessRegistNum);
    }

    // 로그인 ID로 회원 조회
    public Optional<CompanyMember> findByLoginId(String loginId) {
        return companyMemberRepository.findByLoginIdAndDelYn(loginId, "N");
    }

    // ID로 회원 조회
    public Optional<CompanyMember> findById(Long id) {
        return companyMemberRepository.findById(id)
                .filter(m -> "N".equals(m.getDelYn()));
    }

    // 회원 정보 수정
    @Transactional
    public CompanyMember update(CompanyMember member) {
        return companyMemberRepository.save(member);
    }

    // 비밀번호 변경
    @Transactional
    public void changePassword(Long id, String newPassword) {
        companyMemberRepository.findById(id).ifPresent(member -> {
            member.setPassword(passwordEncoder.encode(newPassword));
        });
    }

    // 회원 탈퇴 (soft delete)
    @Transactional
    public void delete(Long id) {
        companyMemberRepository.findById(id).ifPresent(member -> {
            member.setDelYn("Y");
        });
    }

    // 관리자용: 전체 회원 목록
    public List<CompanyMember> findAll() {
        return companyMemberRepository.findByDelYnOrderByIdDesc("N");
    }

    // 관리자용: 승인 대기 회원 목록
    public List<CompanyMember> findPendingApproval() {
        return companyMemberRepository.findByApprovalYnAndDelYnOrderByIdDesc("N", "N");
    }

    // 관리자용: 회원 승인
    @Transactional
    public void approve(Long id) {
        companyMemberRepository.findById(id).ifPresent(member -> {
            member.setApprovalYn("Y");
        });
    }
}
