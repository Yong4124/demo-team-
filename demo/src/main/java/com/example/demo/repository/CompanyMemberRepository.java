package com.example.demo.repository;

import com.example.demo.entity.CompanyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
    
    Optional<CompanyMember> findByLoginIdAndDelYn(String loginId, String delYn);
    
    Optional<CompanyMember> findByLoginId(String loginId);
    
    Optional<CompanyMember> findByEmailAndDelYn(String email, String delYn);
    
    boolean existsByLoginId(String loginId);
    
    boolean existsByEmail(String email);
    
    boolean existsByBusinessRegistNum(String businessRegistNum);
    
    // 관리자용: 회원 목록 조회
    List<CompanyMember> findByDelYnOrderByIdDesc(String delYn);
    
    // 관리자용: 승인 대기 회원
    List<CompanyMember> findByApprovalYnAndDelYnOrderByIdDesc(String approvalYn, String delYn);
}
