package com.example.demo.service;

import com.example.demo.entity.PersonalMember;
import com.example.demo.repository.PersonalMemberRepository;
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
public class PersonalMemberService {

    private final PersonalMemberRepository personalMemberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public PersonalMember register(PersonalMember member) {
        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setInsertDate(LocalDate.now());
        member.setApprovalYn("Y");  // 테스트용 자동 승인 (나중에 "N"으로 변경)
        member.setDelYn("N");
        return personalMemberRepository.save(member);
    }

    // 아이디 중복 확인
    public boolean existsByLoginId(String loginId) {
        return personalMemberRepository.existsByLoginId(loginId);
    }

    // 이메일 중복 확인
    public boolean existsByEmail(String email) {
        return personalMemberRepository.existsByEmail(email);
    }

    // 로그인 ID로 회원 조회
    public Optional<PersonalMember> findByLoginId(String loginId) {
        return personalMemberRepository.findByLoginIdAndDelYn(loginId, "N");
    }

    // ID로 회원 조회
    public Optional<PersonalMember> findById(Long id) {
        return personalMemberRepository.findById(id)
                .filter(m -> "N".equals(m.getDelYn()));
    }

    // 회원 정보 수정
    @Transactional
    public PersonalMember update(PersonalMember member) {
        return personalMemberRepository.save(member);
    }

    // 비밀번호 변경
    @Transactional
    public void changePassword(Long id, String newPassword) {
        personalMemberRepository.findById(id).ifPresent(member -> {
            member.setPassword(passwordEncoder.encode(newPassword));
        });
    }

    // 회원 탈퇴 (soft delete)
    @Transactional
    public void delete(Long id) {
        personalMemberRepository.findById(id).ifPresent(member -> {
            member.setDelYn("Y");
        });
    }

    // 관리자용: 전체 회원 목록
    public List<PersonalMember> findAll() {
        return personalMemberRepository.findByDelYnOrderByIdDesc("N");
    }

    // 관리자용: 승인 대기 회원 목록
    public List<PersonalMember> findPendingApproval() {
        return personalMemberRepository.findByApprovalYnAndDelYnOrderByIdDesc("N", "N");
    }

    // 관리자용: 회원 승인
    @Transactional
    public void approve(Long id) {
        personalMemberRepository.findById(id).ifPresent(member -> {
            member.setApprovalYn("Y");
        });
    }
}
