package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.CompanyMember;
import com.example.demo.entity.PersonalMember;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CompanyMemberRepository;
import com.example.demo.repository.PersonalMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonalMemberRepository personalMemberRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 관리자 확인
        Optional<Admin> admin = adminRepository.findByLoginIdAndDelYn(username, "N");
        if (admin.isPresent()) {
            Admin a = admin.get();
            return new User(
                    a.getLoginId(),
                    a.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // 2. 기업회원 확인
        Optional<CompanyMember> company = companyMemberRepository.findByLoginIdAndDelYn(username, "N");
        if (company.isPresent()) {
            CompanyMember c = company.get();
            // 승인된 회원만 로그인 가능
            if (!"Y".equals(c.getApprovalYn())) {
                throw new UsernameNotFoundException("승인 대기 중인 계정입니다.");
            }
            return new User(
                    c.getLoginId(),
                    c.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_COMPANY"))
            );
        }

        // 3. 개인회원 확인
        Optional<PersonalMember> personal = personalMemberRepository.findByLoginIdAndDelYn(username, "N");
        if (personal.isPresent()) {
            PersonalMember p = personal.get();
            // 승인된 회원만 로그인 가능
            if (!"Y".equals(p.getApprovalYn())) {
                throw new UsernameNotFoundException("승인 대기 중인 계정입니다.");
            }
            return new User(
                    p.getLoginId(),
                    p.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_PERSONAL"))
            );
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
    }
}
