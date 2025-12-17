package com.example.demo.repository;

import com.example.demo.entity.FavoriteJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteJobRepository extends JpaRepository<FavoriteJob, Long> {
    
    // 개인회원별 관심 채용공고 목록
    List<FavoriteJob> findByPersonalMemberIdAndDelYnOrderByIdDesc(Long personalMemberId, String delYn);
    
    // 관심 등록 여부 확인
    boolean existsByPersonalMemberIdAndJobPostingIdAndDelYn(Long personalMemberId, Long jobPostingId, String delYn);
    
    // 특정 관심 조회 (삭제용)
    Optional<FavoriteJob> findByPersonalMemberIdAndJobPostingIdAndDelYn(Long personalMemberId, Long jobPostingId, String delYn);
}
