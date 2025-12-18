package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 로그인 ID로 관리자 조회
    public Optional<Admin> findByLoginId(String loginId) {
        return adminRepository.findByLoginIdAndDelYn(loginId, "N");
    }

    // ID로 관리자 조회
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id)
                .filter(a -> "N".equals(a.getDelYn()));
    }

    // 관리자 등록
    @Transactional
    public Admin register(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setInsertDate(LocalDate.now());
        admin.setDelYn("N");
        return adminRepository.save(admin);
    }

    // 아이디 중복 확인
    public boolean existsByLoginId(String loginId) {
        return adminRepository.existsByLoginId(loginId);
    }
}
