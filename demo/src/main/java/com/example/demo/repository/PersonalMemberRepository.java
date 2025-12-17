package com.example.demo.repository;

import com.example.demo.entity.PersonalMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalMemberRepository extends JpaRepository<PersonalMember, Long> {
    
    Optional<PersonalMember> findByLoginIdAndDelYn(String loginId, String delYn);
    
    Optional<PersonalMember> findByLoginId(String loginId);
    
    Optional<PersonalMember> findByEmailAndDelYn(String email, String delYn);
    
    boolean existsByLoginId(String loginId);
    
    boolean existsByEmail(String email);
    
    // 관리자용: 회원 목록 조회
    List<PersonalMember> findByDelYnOrderByIdDesc(String delYn);
    
    // 관리자용: 승인 대기 회원
    List<PersonalMember> findByApprovalYnAndDelYnOrderByIdDesc(String approvalYn, String delYn);
}
