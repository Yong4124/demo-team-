package com.example.demo.repository;

import com.example.demo.entity.PersonalMember;
import com.example.demo.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
    // 개인회원별 이력서 목록
    List<Resume> findByPersonalMemberAndDelYnOrderByIdDesc(PersonalMember personalMember, String delYn);
    
    // 개인회원 ID로 이력서 목록
    List<Resume> findByPersonalMemberIdAndDelYnOrderByIdDesc(Long personalMemberId, String delYn);
    
    // 최신 이력서 조회 (불러오기용)
    Optional<Resume> findFirstByPersonalMemberIdAndDelYnOrderByIdDesc(Long personalMemberId, String delYn);
    
    // 지원 완료된 이력서만
    List<Resume> findByPersonalMemberIdAndApplyYnAndDelYnOrderByIdDesc(Long personalMemberId, String applyYn, String delYn);
}
