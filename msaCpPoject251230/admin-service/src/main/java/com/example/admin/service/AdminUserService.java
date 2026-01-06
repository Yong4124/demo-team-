package com.example.admin.service;

import com.example.admin.model.AdminRole;
import com.example.admin.model.AdminUser;
import com.example.admin.repository.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AdminUserService {

    private final AdminUserRepository repository;
    private final PasswordEncoder passwordEncoder; // ✅ BCrypt 제거, Bean 주입

    public AdminUserService(AdminUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResult login(String loginId, String rawPassword) {
        AdminUser user = repository.findByLoginIdAndDelYn(loginId, "N")
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        // ✅ PBKDF2(SHA-512)로 matches
        if (!passwordEncoder.matches(rawPassword, user.getPw())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // ✅ 간단 토큰(샘플): 운영에선 JWT 권장
        String token = UUID.randomUUID().toString();

        return new LoginResult(token, user.getNm(), user.getRole().getLabel(), user.getRole().getCode());
    }

    public void ensureDefaultSuperAdmin() {
        System.out.println("### ensureDefaultSuperAdmin called"); // ✅ 여기!

        // 삭제된 계정 제외하고 존재 여부 확인
        if (repository.existsByLoginIdAndDelYn("admin", "N")) return;

        AdminUser admin = new AdminUser();
        admin.setLoginId("admin");

        // ✅ 저장은 PBKDF2(SHA-512) 해시로 저장됨
        admin.setPw(passwordEncoder.encode("admin123"));

        admin.setNm("슈퍼관리자");
        admin.setEmail("admin@local");
        admin.setDepartment("관리자");
        admin.setPhone("010-0000-0000");
        admin.setRole(AdminRole.SUPER_ADMIN);
        admin.setInsertDate(LocalDate.now());
        admin.setDelYn("N");

        repository.save(admin);
    }

    public record LoginResult(String token, String name, String roleName, String roleCode) {}
}
